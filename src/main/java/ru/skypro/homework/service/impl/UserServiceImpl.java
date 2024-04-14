package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

public class UserServiceImpl implements UserService {


    @Override
    public NewPasswordDto changePassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        return null;
    }

    @Override
    public UserDto getMe(Authentication authentication) {
        return null;
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {
        return null;
    }

    @Override
    public void updateImage(Authentication authentication, MultipartFile image) {

    }
}
