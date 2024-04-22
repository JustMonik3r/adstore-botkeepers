package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "avatars")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private byte[] image;
    @OneToOne
    private User user;

    //private Integer user_id;
}
