package com.personalsoft.btgfund.com.api_java.domain.ports.output;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;

public interface IUserAdapter {

    UsersModel saveUser(UsersModel user);
    UsersModel  getByEmail(String email);
    LoginResponseModel authenticate(String email, String password);
    UsersModel decodeToken(String token);
}
