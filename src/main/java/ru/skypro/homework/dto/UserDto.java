package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.entity.Avatar;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private RoleDto role;
    private String avatar;
}
