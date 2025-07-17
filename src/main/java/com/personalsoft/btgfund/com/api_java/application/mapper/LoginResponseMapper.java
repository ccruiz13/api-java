package com.personalsoft.btgfund.com.api_java.application.mapper;

import com.personalsoft.btgfund.com.api_java.application.dto.response.LoginDTOResponse;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface LoginResponseMapper {
    LoginDTOResponse toDto(LoginResponseModel model);
}
