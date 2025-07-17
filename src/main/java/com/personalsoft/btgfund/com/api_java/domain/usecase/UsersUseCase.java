package com.personalsoft.btgfund.com.api_java.domain.usecase;

import com.personalsoft.btgfund.com.api_java.domain.constanst.ValidationMessage;
import com.personalsoft.btgfund.com.api_java.domain.exception.UserDomainException;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.input.IUserService;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IPasswordAdapter;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UsersUseCase implements IUserService {

    private final IPasswordAdapter passwordAdapter;
    private final IUserAdapter userAdapter;

    @Override
    public void createUser(UsersModel model) {

    }

    @Override
    public LoginResponseModel login(String email, String password) {
        loginField(email, password);
        UsersModel user = userAdapter.getByEmail(email);
        if (user == null)
            throw new UserDomainException(ValidationMessage.USER_NOT_FOUND_BY_EMAIL_MESSAGE.getMessage());
        passwordAdapter.validatePassword(password, user.getPassword());
        return userAdapter.authenticate(email, password);
    }

    private void loginField(String email, String password) {


        if (isNullOrEmpty(email)) {
            throw new UserDomainException(ValidationMessage.EMAIL_REQUIRED.getMessage());
        }

        if (!isValidEmail(email)) {
            throw new UserDomainException(ValidationMessage.EMAIL_INVALID.getMessage());
        }


        if (isNullOrEmpty(password)) {
            throw new UserDomainException(ValidationMessage.PASSWORD_REQUIRED.getMessage());
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches(ValidationMessage.REGEX_VALID_EMAIL.getMessage(), email);
    }


}
