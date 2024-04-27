package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.mappers.UserMapper;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageRepository imageRepository;

    // Тест для проверки изменения пароля с правильным текущим паролем
    @Test
    public void testChangePassword() {
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setNewPassword("newPassword");

        User user = new User();
        user.setPassword("oldPassword");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@test.com");
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Mockito.when(userMapper.updateNewPasswordDtoToUser(any())).thenReturn(user);
        Mockito.when(encoder.encode(any())).thenReturn("newPassword");

        userService.changePassword(newPasswordDto, authentication);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("test@test.com");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        assertEquals("newPassword", user.getPassword());
    }


    //Тест, который проверяет возвращаемое значение при вызове метода с правильным Authentication
    @Test
    public void testGetMe() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@test.com");
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Mockito.when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.getMe(authentication);

        Mockito.verify(userRepository, times(1)).findByEmail("test@test.com");
        assertEquals(userDto, result);
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
    public void testUpdateUser() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("John");
        updateUserDto.setLastName("Doe");
        updateUserDto.setPhone("123456789");

        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Jane");
        user.setLastName("Smith");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@test.com");
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        User updatedUser = new User();
        updatedUser.setEmail("test@test.com");
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setPhone("123456789");

        Mockito.when(userRepository.save(user)).thenReturn(updatedUser);

        UpdateUserDto updatedUserDto = new UpdateUserDto();
        updatedUserDto.setFirstName("John");
        updatedUserDto.setLastName("Doe");
        updatedUserDto.setPhone("123456789");

        Mockito.when(userMapper.updateUserToDto(updatedUser)).thenReturn(updatedUserDto);

        UpdateUserDto result = userService.updateUser(updateUserDto, authentication);

        Mockito.verify(userRepository, times(1)).findByEmail("test@test.com");
        Mockito.verify(userRepository, times(1)).save(user);
        assertEquals(updatedUserDto, result);
    }


    //Тест, который проверяет наличие пользователя с таким email и выбрасывание исключения при его отсутствии.
    @Test
    public void testUpdateUserWithInvalidAuthentication() {
        UpdateUserDto updatedUserDto = new UpdateUserDto();
        updatedUserDto.setFirstName("John");
        updatedUserDto.setLastName("Doe");
        updatedUserDto.setPhone("123456789");
        Authentication authentication = new UsernamePasswordAuthenticationToken("invalid@example.com", "password");

        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.updateUser(updatedUserDto, authentication));
    }

    //Тест, который проверяет возвращение изображения пользователя по его id
    @Test
    public void testGetImage_UserFound() {
        User user = new User();
        Image image = new Image();
        image.setData(new byte[]{1, 2, 3});
        user.setImages(image);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        byte[] result = userService.getImage(1);

        Mockito.verify(userRepository, times(1)).findById(1);
        assertEquals(image.getData(), result);
    }

    // Тест проверяет поведение метода getImage сервиса userService в случае,
    // если пользователь не найден в репозитории.
    @Test
    public void testGetImage_UserNotFound() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());

        byte[] result = userService.getImage(1);

        Mockito.verify(userRepository, times(1)).findById(1);
        assertNull(result);
    }
    // Тест проверяет поведение метода getImage сервиса userService в случае,
    // если пользователь с заданным идентификатором не найден в репозитории.
    @Test
    public void testGetImageWithNonExistingUser() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        byte[] result = userService.getImage(2);

        assertNull(result);
    }

    //Тест, который проверяют обновление изображения пользователя.
    @Test
    public void testUpdateImage() throws IOException {

        MultipartFile file = Mockito.mock(MultipartFile.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@test.com");
        User user = new User();
        user.setEmail("test@example.com");

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(file.getSize()).thenReturn(100L);
        Mockito.when(file.getContentType()).thenReturn("image/jpeg");
        Mockito.when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});

        userService.updateImage(authentication, file);

        Mockito.verify(userRepository, times(1)).findByEmail(anyString());
        Mockito.verify(imageRepository, times(1)).save(any(Image.class));
        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }
}