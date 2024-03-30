package com.uncledemy.salesmanagementsystem.authserviceimpl;

import com.uncledemy.salesmanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.salesmanagementsystem.dto.LoginDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.exception.UserNotFoundException;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import com.uncledemy.salesmanagementsystem.security.JwtService;
import com.uncledemy.salesmanagementsystem.security.config.SecureUser;
import com.uncledemy.salesmanagementsystem.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login_ValidCredentials_Success() throws SalesManagementException, UserNotFoundException {
        LoginDto loginDto = new LoginDto("username", "password");
        User user = new User();
        user.setId(1L);

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(jwtService.generateToken(any(SecureUser.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void login_InvalidCredentials_ThrowsSalesManagementException() {
        LoginDto loginDto = new LoginDto("username", "password");

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(loginDto));
    }

    @Test
    void login_AuthenticationFailure_ThrowsSalesManagementException() {
        LoginDto loginDto = new LoginDto("username", "password");

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        assertThrows(SalesManagementException.class, () -> authService.login(loginDto));
    }
}
