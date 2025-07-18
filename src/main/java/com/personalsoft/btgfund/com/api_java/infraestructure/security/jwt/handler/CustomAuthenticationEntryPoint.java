package com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler;

import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        ResponseUtils.write(
                response,
                ResponseMapper.buildError(MessagesResponse.INVALID_CREDENTIALS_ERROR.getMessage(), request.getRequestURI()),
                HttpStatus.UNAUTHORIZED.value()
        );
    }

}
