package com.personalsoft.btgfund.com.api_java.application.handler;

import com.personalsoft.btgfund.com.api_java.application.dto.request.LoginDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.request.UsersDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.application.dto.response.UserDTOResponse;

public interface IUserHandler {
    LoginDTOResponse login(LoginDTO loginDTO);
    void saveUsers(UsersDTO usersDTO);
    UserDTOResponse decode(String token);
}
