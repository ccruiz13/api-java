package com.personalsoft.btgfund.com.api_java.infraestructure.output.jwt.adapter;

import com.personalsoft.btgfund.com.api_java.domain.ports.output.IPasswordAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordAdapter implements IPasswordAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new NoDataFoundException(MessagesResponse.INVALID_CREDENTIALS_ERROR.getMessage());
        }
        return true;
    }
}
