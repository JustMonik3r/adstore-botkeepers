package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.RoleDto;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private RoleDto role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Avatar avatar;
}
