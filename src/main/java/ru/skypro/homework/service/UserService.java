package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface UserService {


    void changePassword(NewPasswordDto newPasswordDto, Authentication authentication);

    UserDto getMe(Authentication authentication);

    UserDto updateUser(UserDto userDto, Authentication authentication);

    UserDto findByEmail(String email);

    void updateAvatar(Authentication authentication, MultipartFile image) throws IOException;

    Avatar getAvatar(Integer id);

    User getUser(String userName);
}
