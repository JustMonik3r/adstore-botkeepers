package ru.skypro.homework.service.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.*;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
@Component
public class UserMapper {

    public UserDto userToUserDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setImage(user.getImage().toString());
        return dto;
    }


    public User userDtoToUser(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        return entity;
    }
}
