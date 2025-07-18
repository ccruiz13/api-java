package com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.SuccessResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.TokenJwtConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


public class ResponseUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ResponseUtils() {
    }

    public static <T> ResponseEntity<T> apiResponses(T responseBody, HttpStatus status) {
        return new ResponseEntity<>(responseBody, status);
    }


    public static void write(HttpServletResponse response, Object body, int status, String token) throws IOException {
        response.setStatus(status);
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        if (token != null) {
            response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, token);
        }
        response.getWriter().write(mapper.writeValueAsString(body));
    }

    public static void write(HttpServletResponse response, Object body, int status) throws IOException {
        write(response, body, status, null);
    }

}
