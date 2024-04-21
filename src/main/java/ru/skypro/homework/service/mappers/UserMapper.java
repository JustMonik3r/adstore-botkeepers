package ru.skypro.homework.service.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.*;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
@Mapper(componentModel = "spring")
public interface UserMapper {

   //@Mapping(source = "firstName", target = "firstName")
    UserDto userToUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    UpdateUserDto updateUserToDto(User user);

}
