package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.adapter;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.CustomUnauthorizedException;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities.UserEntity;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper.UsersEntityMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.repositories.UsersRepositories;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.TokenJwtConfig;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.AuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdapter implements IUserAdapter {

    private final ObjectProvider<AuthenticationFilter> authenticationFilterProvider;
    private final UsersRepositories usersRepositories;

    @Override
    public UsersModel saveUser(UsersModel user) {
        UserEntity entity = UsersEntityMapper.toEntity(user);
        return UsersEntityMapper.toModel(usersRepositories.save(entity));
    }

    @Override
    public UsersModel getByEmail(String email) {
        UserEntity entity = usersRepositories.findByEmail(email);
        return entity != null ? UsersEntityMapper.toModel(entity) : null;
    }

    @Override
    public LoginResponseModel authenticate(String email, String password) {
        AuthenticationFilter authenticationFilter = authenticationFilterProvider.getObject();
        Authentication authentication = authenticationFilter.authenticateUser(email, password);
        return authenticationFilter.generateTokenResponse(authentication);
    }

    @Override
    public UsersModel decodeToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(TokenJwtConfig.SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String email = claims.getSubject();
            return getByEmail(email);
        } catch (JwtException e) {
            throw new CustomUnauthorizedException(MessagesResponse.INVALID_TOKEN_ERROR.getMessage(), e);
        }
    }
}