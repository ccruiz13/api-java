package com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper;

import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.ErrorResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.SuccessResponseDTO;

public class ResponseMapper {

    private ResponseMapper() {
    }

    public static SuccessResponseDTO buildSuccess(String message, Object data) {
        if (data == null)
            return SuccessResponseDTO.builder()
                    .message(message)
                    .build();

        return SuccessResponseDTO.builder()
                .message(message)
                .data(data)
                .build();
    }

    public static ErrorResponseDTO buildError(String message, String error) {
        return ErrorResponseDTO.builder()
                .message(message)
                .error(error)
                .build();
    }
}
