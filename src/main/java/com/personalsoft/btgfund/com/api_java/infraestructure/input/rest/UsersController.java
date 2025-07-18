package com.personalsoft.btgfund.com.api_java.infraestructure.input.rest;

import com.personalsoft.btgfund.com.api_java.application.documentation.OpenApiConstants;
import com.personalsoft.btgfund.com.api_java.application.dto.request.LoginDTO;
import com.personalsoft.btgfund.com.api_java.application.dto.request.UsersDTO;
import com.personalsoft.btgfund.com.api_java.application.handler.IUserHandler;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.EndPointApi;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.mapper.ResponseMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.utils.ResponseUtils;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.CustomUnauthorizedException;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.ErrorResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.input.response.SuccessResponseDTO;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.TokenJwtConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = OpenApiConstants.TAG_USERS_NAME, description = OpenApiConstants.TAG_DESCRIPTION)
@RestController
@RequestMapping(EndPointApi.BASE_URL)
@RequiredArgsConstructor
public class UsersController {

    private final IUserHandler userHandler;

    @PostMapping(EndPointApi.LOGIN)
    @Operation(summary = OpenApiConstants.LOGIN_SUMMARY, description = OpenApiConstants.LOGIN_DESCRIPTION, security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.HTTP_200, description = OpenApiConstants.RESPONSE_200,
                    content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_400, description = OpenApiConstants.RESPONSE_400,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_401, description = OpenApiConstants.RESPONSE_401,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_500, description = OpenApiConstants.RESPONSE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<SuccessResponseDTO> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = OpenApiConstants.LOGIN_REQUEST,
            content = @Content(mediaType = TokenJwtConfig.CONTENT_TYPE, schema = @Schema(implementation = LoginDTO.class)))
                                                    @RequestBody LoginDTO loginDTO) {
        userHandler.login(loginDTO);
        return ResponseUtils.apiResponses(ResponseMapper.buildSuccess(MessagesResponse.LOGIN_SUCCESS_MESSAGE.getMessage()), HttpStatus.OK);
    }

    @PostMapping(EndPointApi.CREATE_USERS)
    @Operation(summary = OpenApiConstants.CREATE_USER_SUMMARY, description = OpenApiConstants.CREATE_USER_DESCRIPTION, security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.HTTP_201, description = OpenApiConstants.RESPONSE_201,
                    content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_400, description = OpenApiConstants.RESPONSE_400,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_401, description = OpenApiConstants.RESPONSE_401,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_500, description = OpenApiConstants.RESPONSE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<SuccessResponseDTO> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = OpenApiConstants.CREATE_USER_REQUEST,
            content = @Content(mediaType = TokenJwtConfig.CONTENT_TYPE, schema = @Schema(implementation = UsersDTO.class)))
                                                    @RequestBody  UsersDTO usersDTO) {
        userHandler.saveUsers(usersDTO);
        return ResponseUtils.apiResponses(ResponseMapper.buildSuccess(MessagesResponse.CREATE_USER__SUCCESS_MESSAGE.getMessage()), HttpStatus.OK);
    }

    @GetMapping(EndPointApi.DECODE)
    @Operation(summary = OpenApiConstants.DECODE_SUMMARY, description = OpenApiConstants.DECODE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.HTTP_200, description = OpenApiConstants.RESPONSE_200,
                    content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_400, description = OpenApiConstants.RESPONSE_400,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_401, description = OpenApiConstants.RESPONSE_401,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = OpenApiConstants.HTTP_500, description = OpenApiConstants.RESPONSE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<SuccessResponseDTO> decode(HttpServletRequest request) {

        String authHeader  = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            throw new CustomUnauthorizedException(MessagesResponse.INVALID_TOKEN_ERROR.getMessage());
        }
        String token = authHeader.replace(TokenJwtConfig.PREFIX_TOKEN, "").trim();
        return ResponseUtils.apiResponses(ResponseMapper.buildSuccess(MessagesResponse.LOGIN_SUCCESS_MESSAGE.getMessage(), userHandler.decode(token)), HttpStatus.OK);
    }

}
