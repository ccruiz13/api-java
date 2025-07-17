package com.personalsoft.btgfund.com.api_java.infraestructure.output.jwt.adapter;

import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.MessagesResponse;
import com.personalsoft.btgfund.com.api_java.infraestructure.exception.NoDataFoundException;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailsAdapter implements UserDetailsService {

    private final IUserAdapter userAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersModel usersModel = userAdapter.getByEmail(username);
        if (usersModel == null)
            throw new NoDataFoundException(MessagesResponse.USERNAME_NOT_FOUND_EXCEPTION.getMessage() + username);

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usersModel.getRole())
        );

        return new AuthenticatedUser(
                usersModel.getUserId(),
                usersModel.getEmail(),
                usersModel.getPassword(),
                authorities
        );
    }
}
