package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.UserIllegalArgumentException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.mappers.UserMapper;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageRepository imageRepository;

    // тест для проверки изменения пароля с правильным текущим паролем
    @Test
    public void testChangePasswordCorrectPassword() {
        User user = new User();
        user.setId(1);
        user.setPassword("oldPassword");

        NewPasswordDto newPasswordDto = new NewPasswordDto(1, "oldPassword", "newPassword");

        when(userRepository.findById(newPasswordDto.getId())).thenReturn(Optional.of(user));

        userService.changePassword(newPasswordDto, null);

        assertEquals("newPassword", user.getPassword());
    }

    //Тест для проверки выбрасывания исключения при вводе неверного текущего пароля
    @Test
    public void testChangePasswordIncorrectPassword() {
        User user = new User();
        user.setId(1);
        user.setPassword("oldPassword");

        NewPasswordDto newPasswordDto = new NewPasswordDto(1, "wrongPassword", "newPassword");

        when(userRepository.findById(newPasswordDto.getId())).thenReturn(Optional.of(user));

        assertThrows(UserIllegalArgumentException.class, () -> userService.changePassword(newPasswordDto, null));
    }

    //Тест, который проверяет возвращаемое значение при вызове метода с правильным Authentication
    @Test
    public void testGetMeWithValidAuthentication() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");

        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(new UserDto(1L, "test@example.com"));

        UserDto result = userService.getMe(authentication);

        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
    }

    //Тест, выбрасывает исключения при отсутствии пользователя с таким email.
    @Test
    public void testGetMeWithInvalidAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("invalid@example.com", "password");

        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getMe(authentication));
    }

    // Тест, который проверяет обновление данных пользователя при вызове метода с правильным Authentication
    @Test
    public void testUpdateUserWithValidAuthentication() {
        UpdateUserDto updateUserDto = new UpdateUserDto("John", "Doe", "1234567890");
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");

        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        when(userMapper.updateUserToDto(user)).thenReturn(new UpdateUserDto("John", "Doe", "1234567890"));

        UpdateUserDto result = userService.updateUser(updateUserDto, authentication);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("1234567890", result.getPhone());
    }

    //Тест, который проверяет наличие пользователя с таким email и выбрасывание исключения при его отсутствии.
    @Test
    public void testUpdateUserWithInvalidAuthentication() {
        UpdateUserDto updateUserDto = new UpdateUserDto("John", "Doe", "1234567890");
        Authentication authentication = new UsernamePasswordAuthenticationToken("invalid@example.com", "password");

        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.updateUser(updateUserDto, authentication));
    }

    //Тест, который проверяет возвращение изображения пользователя по его id
//    @Test
//    public void testGetImageWithExistingUser() {
//        User user = new User();
//        user.setId(1);
//
//        Image image = new Image();
//        image.setId(1);
//        image.setData(new byte[]{1, 2, 3, 4});
//
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(user.getImages()).thenReturn(image);
//
//        byte[] result = userService.getImage(1);
//
//        assertEquals(image.getData(), result);
//    }

    @Test
    public void testGetImageWithNonExistingUser() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        byte[] result = userService.getImage(2);

        assertEquals(null, result);
    }

    //Тест, который проверяют обновление изображения пользователя.
//    @Test
//    public void testUpdateImage() throws IOException {
//        User user = new User();
//        user.setEmail("test@example.com");
//
//        Image image = new Image();
//        image.setId(1);
//
//        MultipartFile file = Mockito.mock(MultipartFile.class);
//        when(file.getSize()).thenReturn(100L);
//        when(file.getContentType()).thenReturn("image/jpeg");
//        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3, 4});
//
//        Authentication authentication = Mockito.mock(Authentication.class);
//        when(authentication.getName()).thenReturn("test@example.com");
//
//        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
//        when(user.getImages()).thenReturn(image);
//
//        userService.updateImage(authentication, file);

  //  Mockito.verify(imageRepository).save(any(Image.class));
//    Mockito.verify(userRepository).save(any(User.class));
//    }
}