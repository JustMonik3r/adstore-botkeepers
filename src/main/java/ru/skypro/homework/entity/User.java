package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.RoleDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleDto role;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image images;
    @OneToMany(mappedBy = "users")
    private List<Ad> adEntityList;
}
