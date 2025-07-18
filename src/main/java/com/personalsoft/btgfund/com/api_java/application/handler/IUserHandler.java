package com.personalsoft.btgfund.com.api_java.application.handler;

import com.personalsoft.btgfund.com.api_java.application.dto.request.LoginDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;

public interface IUserHandler {
    LoginDTOResponse login(LoginDTO loginDTO);
}
