package com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.btgfund.com.api_java.application.dto.request.LoginDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.NoDataFoundException;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.SuccessResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.TokenJwtConfig;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.model.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;


@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private static final String AUTHORITIES = "authorities";
    private static final String IDUSER = "idUser";
    private static final String ROLE = "role";
    private static final String USERNAME = "username";

    public Authentication authenticateUser(String email, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    public LoginResponseModel generateTokenResponse(Authentication authResult) {
        try {
            AuthenticatedUser user = (AuthenticatedUser) authResult.getPrincipal();
            String username = user.getUsername();
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

            Claims claims = Jwts.claims()
                    .add(AUTHORITIES, new ObjectMapper().writeValueAsString(roles))
                    .add(USERNAME, username)
                    .add(IDUSER, user.getIdUser())
                    .add(ROLE, user.getRol())
                    .build();

            String token = Jwts.builder()
                    .subject(username)
                    .claims(claims)
                    .expiration(new Date(System.currentTimeMillis() + 3600000))
                    .issuedAt(new Date())
                    .signWith(TokenJwtConfig.SECRET_KEY)
                    .compact();

            return new LoginResponseModel(user.getIdUser(), username, username, token);
        } catch (Exception e) {
            throw new NoDataFoundException(MessagesResponse.INVALID_TOKEN_ERROR.getMessage(), e);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginDTO user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            return authenticateUser(user.getEmail(), user.getPassword());
        } catch (IOException e) {
            throw new NoDataFoundException(MessagesResponse.INVALID_CREDENTIALS_ERROR.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        LoginDTOResponse body = ResponseMapper.toDto(generateTokenResponse(authResult));
        String token = TokenJwtConfig.PREFIX_TOKEN + body.getToken();
        SuccessResponseDTO responseDTO = ResponseMapper.buildSuccess(MessagesResponse.LOGIN_SUCCESS_MESSAGE.getMessage(), body);
        ResponseUtils.write(response, responseDTO, HttpStatus.OK.value(), token);
    }
}