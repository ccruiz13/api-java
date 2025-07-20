package com.personalsoft.btgfund.com.api_java.infraestructure.exceptionhandler;

import com.personalsoft.btgfund.com.api_java.domain.exception.UserDomainException;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.CustomUnauthorizedException;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.ErrorResponseDTO;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ErrorResponseDTO> handleDomainException(UserDomainException ex) {
        return ResponseUtils.apiResponses(ResponseMapper.buildError(ex.getMessage(),ex.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleInfrastructureException(IllegalArgumentException ex) {
        return ResponseUtils.apiResponses(ResponseMapper.buildError(ex.getMessage(),ex.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAutenticationException(CustomUnauthorizedException ex) {
        return ResponseUtils.apiResponses(ResponseMapper.buildError(ex.getMessage(),ex.toString()), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return ResponseUtils.apiResponses(ResponseMapper.buildError(ex.getMessage(),ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
