package ru.skypro.homework.service.mappers;

import org.mapstruct.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "image", expression = "java(user.getImages() != null ? \"/avatars/\"+user.getId() : null)")
    UserDto userToUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "password",source = "newPassword")
    public abstract User updateNewPasswordDtoToUser(NewPasswordDto newPasswordDto);

    UpdateUserDto updateUserToDto(User user);
}
