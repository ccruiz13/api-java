package com.personalsoft.btgfund.com.api_java.application.mapper;


import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.application.dto.response.UserDTOResponse;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
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

    public static UserDTOResponse userDTOResponse(UsersModel model) {
        return UserDTOResponse.builder()
                .userId(model.getUserId())
                .email(model.getEmail())
                .password(model.getPassword())
                .role(model.getRole())
                .build();
    }
}
