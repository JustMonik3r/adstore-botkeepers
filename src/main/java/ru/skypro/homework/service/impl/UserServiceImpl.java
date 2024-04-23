package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.UserIllegalArgumentException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mappers.UserMapper;

import java.io.*;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageRepository imageRepository;



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
    public byte[] getImage(Integer id) {
        return userRepository.findById(id).map(User::getImages).map(Image::getData).orElse(null);
    }

    @Override
    public void updateImage(Authentication authentication, MultipartFile file) throws IOException {

        User users = userRepository.findByEmail(authentication.getName()).get();

        Image image = Optional.ofNullable(users.getImages()).orElseGet(Image::new);
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);

        users.setImages(image);
        userRepository.save(users);
    }

}
