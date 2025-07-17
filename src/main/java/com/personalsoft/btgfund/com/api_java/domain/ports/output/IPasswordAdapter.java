package com.personalsoft.btgfund.com.api_java.domain.ports.output;

public interface IPasswordAdapter {

    String encryptPassword(String password);
    boolean validatePassword(String inputPassword, String storedPassword);

}
