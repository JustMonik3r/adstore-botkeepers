package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.UserIllegalArgumentException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mappers.UserMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public void changePassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        User user = userRepository.findById(newPasswordDto.getId()).get();
        if(user.getPassword().equals(newPasswordDto.getCurrentPassword())) {
            user.setPassword(newPasswordDto.getNewPasswordDto());
            userRepository.save(user);
        } else {
            throw new UserIllegalArgumentException("Пользователь вводит неверный текущий пароль");
        }
    }

    @Override
    public UserDto getMe(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).map(userMapper::userToUserDto).orElseThrow();
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.updateUserToDto(user);
    }

    @Override
    public void updateImage(Authentication authentication, MultipartFile image) {

    }
}
