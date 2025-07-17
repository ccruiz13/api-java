package com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.ErrorResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.TokenJwtConfig;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.model.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ValidationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final IUserAdapter userAdapter;

    private static final String ERROR = "error";

    public ValidationFilter(AuthenticationManager authenticationManager,
                            ObjectMapper objectMapper,
                            IUserAdapter userAdapter) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.userAdapter = userAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(TokenJwtConfig.SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = claims.getSubject();
            String authoritiesJson = (String) claims.get("authorities");
            List<Map<String, String>> rolesList = objectMapper.readValue(authoritiesJson,
                    new TypeReference<>() {});
            List<SimpleGrantedAuthority> authorities = rolesList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                    .collect(Collectors.toList());

            UsersModel user = userAdapter.getByEmail(username);

            AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                    user.getUserId(),
                    username,
                    null,
                    authorities
            );
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authenticatedUser, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);
        }catch (JwtException e) {
            ErrorResponseDTO body = ResponseMapper.buildError(MessagesResponse.INVALID_TOKEN_ERROR.getMessage(),e.getMessage());
            ResponseUtils.write(response, body, HttpStatus.UNAUTHORIZED.value());
        }

    }


}
