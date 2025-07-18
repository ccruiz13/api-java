package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities.UserEntity;

public class UsersEntityMapper {

    private UsersEntityMapper() {
    }

    public static UserEntity toEntity(UsersModel model){

        return UserEntity.builder()
                .userId(model.getUserId())
                .email(model.getEmail())
                .password(model.getPassword())
                .role(model.getRole())
                .build();

    }
    public static UsersModel toModel(UserEntity userEntity){

        return UsersModel.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();

    }
}
