package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {


    void changePassword(NewPassword newPassword, Authentication authentication);

    UserDto getMe(Authentication authentication);

    UpdateUser updateUser(UpdateUser updateUser, Authentication authentication);

    void updateImage(Authentication authentication, MultipartFile image);
}
