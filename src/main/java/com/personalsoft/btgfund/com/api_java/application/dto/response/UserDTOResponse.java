package com.personalsoft.btgfund.com.api_java.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {

    private String userId;
    private String email;
    private String password;
    private String role;
}
