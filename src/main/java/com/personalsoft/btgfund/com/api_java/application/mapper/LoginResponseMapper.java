package com.personalsoft.btgfund.com.api_java.application.mapper;


import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;

public class LoginResponseMapper {

    private LoginResponseMapper() {
    }

    public static LoginDTOResponse toDto(LoginResponseModel model) {
        return new LoginDTOResponse(model.getUserId(),
                model.getEmail(),
                model.getRole(),
                model.getToken());
    }
}
