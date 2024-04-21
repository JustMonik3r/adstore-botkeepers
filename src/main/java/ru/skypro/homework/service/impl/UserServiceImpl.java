package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.UserIllegalArgumentException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mappers.UserMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AvatarRepository avatarRepository;



    @Override
    public void changePassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        if(user.getPassword().equals(newPasswordDto.getCurrentPassword())) {
            user.setPassword(newPasswordDto.getNewPasswordDto());
            userRepository.save(user);
        } else {
            throw new UserIllegalArgumentException("Пользователь вводит неверный текущий пароль");
        }
    }

    @Override
    public UserDto getMe(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).map(userMapper::userToUserDto).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public void updateAvatar(Authentication authentication, MultipartFile file) throws IOException {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        Path filePath = Path.of("./image", authentication.getName());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar;
        if(user.getAvatar() == null) {
            avatar = new Avatar();
        }
        else {
            Integer id = user.getAvatar().getId();
            avatar = avatarRepository.findById(id).orElseThrow();
        }
        avatar.setImage(file.getBytes());
        avatarRepository.save(avatar);
        user.setAvatar(avatar);
        userRepository.save(user);
    }

    /**
     Retrieves the UserDto object for the user with the specified email.
     @param email The email of the user.
     @return The UserDto object representing the user.
     @throws UserNotFoundException if the user is not found.
     */
    @Override
    public UserDto findByEmail(String email) {
        User findedUser = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        return userMapper.userToUserDto(findedUser);
    }

    /**
     Retrieves the image data of the user with the specified ID.
     @param id The ID of the user.
     @return The byte array representing the image data.
     @throws IOException if an I/O error occurs while reading the image data.
     */
    @Override
    public Avatar getAvatar(Integer id) {
        return userRepository.findById(Long.valueOf(id)).get().getAvatar();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
    }
}
