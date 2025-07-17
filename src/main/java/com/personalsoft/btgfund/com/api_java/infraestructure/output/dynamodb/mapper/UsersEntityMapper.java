package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities.Users;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UsersEntityMapper {

    Users toEntity(UsersModel model);
    UsersModel toModel(Users users);
}
