package ru.skypro.homework.controller;

//import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.io.IOException;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
//@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword, Authentication authentication) {

        userService.changePassword(newPassword,authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(Authentication authentication) {
        UserDto user = userService.getMe(authentication);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser, Authentication authentication) {
        UpdateUser foundUpdateUser = userService.updateUser(updateUser,authentication);
        if (foundUpdateUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundUpdateUser);
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage (Authentication authentication, @RequestParam MultipartFile image) throws IOException {
        userService.updateImage(authentication, image);
        return ResponseEntity.ok().build();
    }
}
