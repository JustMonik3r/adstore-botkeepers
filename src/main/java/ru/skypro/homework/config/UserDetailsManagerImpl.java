package ru.skypro.homework.config;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDetailsManagerImpl implements UserDetailsManager {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow();
    }

    @Override
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(RoleDto.valueOf(userDetails.getAuthorities().iterator().next().getAuthority().substring(5)));
        userRepository.save(user);
    }

    public void createUser(RegisterDto registerDto, String pass) {
        User user = new User();
        user.setPassword(pass);
        user.setEmail(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhone(registerDto.getPhone());
        user.setRole(registerDto.getRole());
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }
}