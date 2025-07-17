package com.personalsoft.btgfund.com.api_java.domain.ports.output;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;

public interface IUserAdapter {

    UsersModel saveUser(UsersModel user);
    UsersModel  getByEmail(String email);
}
