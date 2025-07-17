package com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler;

import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtils.write(response, ResponseMapper.buildError(MessagesResponse.INVALID_TOKEN_ERROR.getMessage(),request.getRequestURI()), HttpStatus.UNAUTHORIZED.value());
    }
}
