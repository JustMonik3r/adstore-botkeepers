package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface UserService {


    void changePassword(NewPasswordDto newPasswordDto, Authentication authentication);

    UserDto getMe(Authentication authentication);

    UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication);

    void updateImage(Authentication authentication, MultipartFile file) throws IOException;

    byte[] getImage(Integer id);
}
