package ru.skypro.homework.config;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.mappers.UserMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CastUserDetailsManagerTest {
    @InjectMocks
    private CastUserDetailsManager castUserDetailsManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    //Тест проверяет создание пользователя и обработка нулевого значения RegisterDto.
    @Test
    void createUser_Success() {
        // Arrange
        CastUserDetailsManager userDetailsManager = new CastUserDetailsManager(userRepository);
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("test@gmail.com");
        registerDto.setPassword("password");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setPhone("79905678900");
        registerDto.setRole(RoleDto.valueOf("USER"));


        // Act
        userDetailsManager.createUser(registerDto);

        // Assert
        verify(userRepository).save(Mockito.any(User.class));
    }


    // обработка нулевого значения RegisterDto.
//        @Test
//    void createUser_NullRegisterDto() {
//        // Arrange
//        CastUserDetailsManager userDetailsManager = new CastUserDetailsManager(userRepository);
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> userDetailsManager.createUser(null));
//    }




    //  В этом тесте мы используем Mockito для мокирования UserRepository
    //  и проверяем метод userExists в CastUserDetailsManager, убеждаясь,
    //  что он возвращает true при наличии пользователя с заданным именем пользователя.
    @Test
    void testUserExists() {
        // Arrange
        String username = "test@example.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(new User()));

        // Act
        boolean userExists = castUserDetailsManager.userExists(username);

        // Assert
        assertTrue(userExists);
    }

    // Тест проверяет загрузку пользователя по имени пользователя при его обнаружении
    @Test
    void loadUserByUsername_UserFound() {
        // Arrange
        CastUserDetailsManager userDetailsManager = new CastUserDetailsManager(userRepository);
        User user = new User();
        user.setEmail("test@gmail.com");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsManager.loadUserByUsername("test@gmail.com");

        // Assert
        assertEquals(user.getEmail(), userDetails.getUsername());
    }


//    // обработка случая, когда пользователь не найден
//        @Test
//    void loadUserByUsername_UserNotFound() {
//        // Arrange
//        CastUserDetailsManager userDetailsManager = new CastUserDetailsManager(userRepository);
//        Mockito.when(userRepository.findByEmail("nonexistent@gmail.com")).thenReturn(null);
//
//        // Act & Assert
//        assertThrows(UsernameNotFoundException.class, () -> userDetailsManager.loadUserByUsername("nonexistent@gmail.com"));
//    }
}