package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.config.CastUserDetails;
import ru.skypro.homework.config.CastUserDetailsManager;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private CastUserDetailsManager manager;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testLogin_WithValidCredentials() {
        // Arrange
        String userName = "testUser";
        String password = "password123";

        User user = new User();
        user.setEmail(userName);
        user.setPassword(password);

        UserDetails userDetails = new CastUserDetails(user);

        when(manager.userExists(userName)).thenReturn(true);
        when(manager.loadUserByUsername(userName)).thenReturn(userDetails);
        when(encoder.matches(password, userDetails.getPassword())).thenReturn(true);

        // Act
        boolean result = authService.login(userName, password);

        // Assert
        assertTrue(result);
    }

    @Test
    void testLogin_WithInvalidCredentials() {
        // Arrange
        String userName = "testUser";
        String password = "password123";

        User user = new User();
        user.setEmail(userName);
        user.setPassword(password);

        when(manager.userExists(userName)).thenReturn(true);
        UserDetails userDetails = new CastUserDetails(user);
        when(manager.loadUserByUsername(userName)).thenReturn(userDetails);
        when(encoder.matches(password, userDetails.getPassword())).thenReturn(false);

        // Act
        boolean result = authService.login(userName, password);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRegister_WithNewUser() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setPassword("password123");

        when(manager.userExists(registerDto.getUsername())).thenReturn(false);
        when(encoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        // Act
        boolean result = authService.register(registerDto);

        // Assert
        assertTrue(result);
    }

    @Test
    void testRegister_WithExistingUser() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setPassword("password123");

        when(manager.userExists(registerDto.getUsername())).thenReturn(true);

        // Act
        boolean result = authService.register(registerDto);

        // Assert
        assertFalse(result);
    }
}