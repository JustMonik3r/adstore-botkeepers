package ru.skypro.homework.service.mappers;

import org.mapstruct.*;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "image", expression = "java(user.getImages() != null ? \"/avatars/\"+user.getId() : null)")
    UserDto userToUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    UpdateUserDto updateUserToDto(User user);
}
