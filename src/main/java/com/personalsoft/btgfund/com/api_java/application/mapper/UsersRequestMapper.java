package com.personalsoft.btgfund.com.api_java.application.mapper;

import com.personalsoft.btgfund.com.api_java.application.dto.request.UsersDTO;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;

public class UsersRequestMapper {
    private UsersRequestMapper() {

    }

    public static UsersModel toModel(UsersDTO dto) {
        return UsersModel.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }
}
