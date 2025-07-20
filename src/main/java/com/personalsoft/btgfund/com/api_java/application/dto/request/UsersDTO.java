package com.personalsoft.btgfund.com.api_java.application.dto.request;

import com.personalsoft.btgfund.com.api_java.application.documentation.OpenApiConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    @Schema(description = OpenApiConstants.EMAIL_DESCRIPTION, example = OpenApiConstants.EMAIL_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(description = OpenApiConstants.PASSWORD_DESCRIPTION, example = OpenApiConstants.PASSWORD_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(
            description = OpenApiConstants.ROLE_DESCRIPTION,
            example = OpenApiConstants.ROLE_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = { "ADMIN", "USER", "AUDITOR" }
    )
    private String role;

}
