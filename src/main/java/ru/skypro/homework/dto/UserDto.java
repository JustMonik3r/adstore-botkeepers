package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private RoleDto role;
    private String image;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
    public UserDto(){}

}
