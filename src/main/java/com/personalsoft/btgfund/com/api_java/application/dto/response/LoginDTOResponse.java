package com.personalsoft.btgfund.com.api_java.application.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTOResponse {

    private String userId;
    private String email;
    private String role;
    private String token;
}
