package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.CastUserDetailsManager;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final CastUserDetailsManager manager;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(CastUserDetailsManager manager,
                           PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.encoder = passwordEncoder;
    }

    /**
     * Attempts to authenticate a user by verifying the provided username and password
     * @param userName the username of the user
     * @param password the password of the user
     * @return true if the authentication is successful, false otherwise
     */
    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName )) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }


    /**
     * Registers a new user with the system
     * @param register the registration details of the user
     * @return rue if the registration is successful, false if a user with the same username already exists
     */
    @Override
    public boolean register(RegisterDto register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        register.setPassword(encoder.encode(register.getPassword()));
        manager.createUser(register);
        return true;
    }

}
