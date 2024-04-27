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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;


    /**
     * Sets the password for the user
     * @param newPassword The new password to be set
     * @param authentication The authentication object representing the current user
     */
    @Override
    public void changePassword(NewPasswordDto newPassword, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        User infoToUpdate = userMapper.updateNewPasswordDtoToUser(newPassword);
        user.setPassword(encoder.encode(infoToUpdate.getPassword()));
        userRepository.save(user);
    }

    /**
     * Retrieves the UserDto object representing the logged-in user
     * @param authentication The authentication object representing the current user
     * @return The UserDto object representing the logged-in user
     */
    @Override
    public UserDto getMe(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).map(userMapper::userToUserDto).orElseThrow();
    }

    /**
     * Updates the user information
     * @param updateUserDto The UpdateUserDto object containing the updated user information
     * @param authentication The authentication object representing the current user
     * @return The UpdateUserDto object representing the updated user information
     */
    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.updateUserToDto(user);
    }

    /**
     * Retrieves the image data of the user
     * @param id - The ID of the user
     * @return The byte array representing the image data
     */
    @Override
    public byte[] getImage(Integer id) {
        return userRepository.findById(id).map(User::getImages).map(Image::getData).orElse(null);
    }

    /**
     * Updates the user avatar
     * @param authentication The authentication object representing the current user
     * @param file The MultipartFile object representing the new user avatar image
     * @throws IOException error occurs while reading the image data
     */
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
