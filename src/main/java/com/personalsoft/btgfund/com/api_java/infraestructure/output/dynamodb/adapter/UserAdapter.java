package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.adapter;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities.Users;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper.UsersEntityMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.repositories.UsersRepositories;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class UserAdapter implements IUserAdapter {

    private final AuthenticationFilter authenticationFilter;
    private final UsersEntityMapper usersEntityMapper;
    private final UsersRepositories usersRepositories;


    @Override
    public UsersModel saveUser(UsersModel user) {
        Users entity = usersEntityMapper.toEntity(user);
        return usersEntityMapper.toModel(usersRepositories.save(entity));
    }

    @Override
    public UsersModel  getByEmail(String email) {
        Users entity = usersRepositories.findByEmail(email);
        return entity != null ? usersEntityMapper.toModel(entity) : null;
    }

    @Override
    public LoginResponseModel authenticate(String email, String password) {
       Authentication authentication = authenticationFilter.authenticateUser(email, password);
        return authenticationFilter.generateTokenResponse(authentication);
    }
}
