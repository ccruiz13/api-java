package com.personalsoft.btgfund.com.api_java.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersModel {

    private String userId;
    private String email;
    private String password;
    private String role;
}
