package com.personalsoft.btgfund.com.api_java.application.handler.impl;

import com.personalsoft.btgfund.com.api_java.application.dto.request.LoginDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.request.UsersDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.application.handler.IUserHandler;
import com.personalsoft.btgfund.com.api_java.application.mapper.LoginResponseMapper;
import com.personalsoft.btgfund.com.api_java.application.mapper.UsersRequestMapper;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.input.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserService userService;
    @Override
    public LoginDTOResponse login(LoginDTO loginDTO) {
        return LoginResponseMapper.toDto(userService.login(loginDTO.getEmail(), loginDTO.getPassword()));
    }

    @Override
    public void saveUsers(UsersDTO usersDTO) {
        UsersModel model = UsersRequestMapper.toModel(usersDTO);
        userService.createUser(model);
    }
}
