package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UpdateUserDto {

    private String firstName; // имя пользователя
    private String lastName; // фамилия пользователя
    private String phone; // телефон пользователя

    public UpdateUserDto(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    public UpdateUserDto(){}
}
