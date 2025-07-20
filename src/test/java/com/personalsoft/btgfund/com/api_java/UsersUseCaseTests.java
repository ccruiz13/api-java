package com.personalsoft.btgfund.com.api_java;


import com.personalsoft.btgfund.com.api_java.domain.exception.UserDomainException;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.model.response.LoginResponseModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IPasswordAdapter;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.domain.usecase.UsersUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersUseCaseTests {

	@Mock
	private  IPasswordAdapter passwordAdapter;

	@Mock
	private IUserAdapter userAdapter;

	@InjectMocks
	private UsersUseCase usersUseCase;

	private UsersModel validUser;

	@BeforeEach
	void setUp(){
		usersUseCase = new UsersUseCase(passwordAdapter, userAdapter);
		validUser = new UsersModel();
		validUser.setEmail("test@example.com");
		validUser.setPassword("password123");
		validUser.setRole("USER");
	}

	@Test
	void createUser_success() {
		when(userAdapter.getByEmail("test@example.com")).thenReturn(null);
		when(passwordAdapter.encryptPassword("password123")).thenReturn("encrypted");

		assertDoesNotThrow(() -> usersUseCase.createUser(validUser));

		verify(userAdapter).saveUser(validUser);
	}

	@Test
	void createUser_emailNull_throws() {
		validUser.setEmail(null);
		assertThrows(UserDomainException.class, () -> usersUseCase.createUser(validUser));
	}

	@Test
	void login_success() {
		UsersModel user = new UsersModel();
		user.setPassword("hashed");
		when(userAdapter.getByEmail("test@example.com")).thenReturn(user);
		when(userAdapter.authenticate("test@example.com", "password123"))
				.thenReturn(new LoginResponseModel());

		assertDoesNotThrow(() -> usersUseCase.login("test@example.com", "password123"));
	}

	@Test
	void login_emailNull_throws() {
		assertThrows(UserDomainException.class, () -> usersUseCase.login(null, "pass"));
	}


	@Test
	void login_emailInvalid_throws() {
		assertThrows(UserDomainException.class, () -> usersUseCase.login("invalid", "pass"));
	}

	@Test
	void login_passwordNull_throws() {
		assertThrows(UserDomainException.class, () -> usersUseCase.login("test@example.com", null));
	}

	@Test
	void login_userNotFound_throws() {
		when(userAdapter.getByEmail("test@example.com")).thenReturn(null);
		assertThrows(UserDomainException.class, () -> usersUseCase.login("test@example.com", "pass"));
	}

	@Test
	void decode_success() {
		String token = "valid.token.value";
		UsersModel user = new UsersModel();
		when(userAdapter.decodeToken(token)).thenReturn(user);

		assertEquals(user, usersUseCase.decode(token));
	}

}
