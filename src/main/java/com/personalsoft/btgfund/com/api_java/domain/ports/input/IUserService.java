package com.personalsoft.btgfund.com.api_java.domain.ports.input;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;

public interface IUserService {

    void createUser(UsersModel model);

    LoginResponseModel login(String email, String password);

    UsersModel decode(String token);
}
