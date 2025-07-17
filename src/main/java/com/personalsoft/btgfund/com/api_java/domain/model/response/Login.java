package com.personalsoft.btgfund.com.api_java.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private String userId;
    private String email;
    private String role;
    private String token;
}
