package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mappers.UserMapper;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;
//private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.userMapper = userMapper;
    }

    @Override
    public void changePassword(NewPassword newPassword, Authentication authentication) {
        User user = userRepository.findById(newPassword.getId()).get();
        if(user.getPassword().equals(newPassword.getCurrentPassword())) {
            user.setPassword(newPassword.getNewPassword());
        } else new IllegalArgumentException();
        userRepository.save(user);
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
