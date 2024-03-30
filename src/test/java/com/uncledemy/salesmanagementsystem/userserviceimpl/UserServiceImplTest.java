package com.uncledemy.salesmanagementsystem.userserviceimpl;

import com.uncledemy.salesmanagementsystem.dto.StaffDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.salesmanagementsystem.exception.InvalidUsernameException;
import com.uncledemy.salesmanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.salesmanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import com.uncledemy.salesmanagementsystem.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void staffRegistration_ValidStaffDto_Success() throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException {
        StaffDto staffDto = new StaffDto();
        staffDto.setName("Johnson Michael");
        staffDto.setUsername("Mick");
        staffDto.setPassword("Password@123");
        staffDto.setConfirmPassword("Password@123");
        staffDto.setAuthority("ROLE_ADMIN");

        when(userRepository.findUserByUsername(anyString()))
                .thenReturn(Optional.of(new User()));

        when(passwordEncoder.encode(staffDto.getPassword())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userService.staffRegistration(staffDto));
    }


    @Test
    void staffRegistration_InvalidPassword_ThrowsInvalidPasswordException() {
        StaffDto staffDto = new StaffDto();
        staffDto.setName("John Doe");
        staffDto.setUsername("john");
        staffDto.setPassword("password");

        assertThrows(InvalidPasswordException.class, () -> userService.staffRegistration(staffDto));
    }

    @Test
    void staffRegistration_UserAlreadyExist_ThrowsUserAlreadyExistException() {
        StaffDto staffDto = new StaffDto();
        staffDto.setName("John Doe");
        staffDto.setUsername("john");
        staffDto.setPassword("Password@123");
        staffDto.setConfirmPassword("Password@123");
        staffDto.setAuthority("ROLE_ADMIN");

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> userService.staffRegistration(staffDto));
    }

    @Test
    void staffRegistration_PasswordMismatch_ThrowsPasswordMisMatchException() {
        StaffDto staffDto = new StaffDto();
        staffDto.setName("John Doe");
        staffDto.setUsername("john");
        staffDto.setPassword("Password@123");
        staffDto.setConfirmPassword("Password@456");

        assertThrows(PasswordMisMatchException.class, () -> userService.staffRegistration(staffDto));
    }

    @Test
    void loadUser_ValidUsername_ReturnsUser() throws InvalidUsernameException {
        String username = "john";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findUserByUsernameIgnoreCase(username)).thenReturn(user);

        assertNotNull(userService.loadUser(username));
    }
}
