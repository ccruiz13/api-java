package com.personalsoft.btgfund.com.api_java.application.dto.request;

import com.personalsoft.btgfund.com.api_java.application.documentation.OpenApiConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @Schema(description = OpenApiConstants.EMAIL_DESCRIPTION, example = OpenApiConstants.EMAIL_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(description = OpenApiConstants.PASSWORD_DESCRIPTION, example = OpenApiConstants.PASSWORD_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
