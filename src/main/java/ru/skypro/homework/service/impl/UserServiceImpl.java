package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

public class UserServiceImpl implements UserService {


    @Override
    public NewPassword changePassword(NewPassword newPassword, Authentication authentication) {
        return null;
    }

    @Override
    public UserDto getMe(Authentication authentication) {
        return null;
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser, Authentication authentication) {
        return null;
    }

    @Override
    public void updateImage(Authentication authentication, MultipartFile image) {

    }
}
