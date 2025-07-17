package com.personalsoft.btgfund.com.api_java.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersModel {

    private String userId;
    private String email;
    private String password;
    private String role;
}
