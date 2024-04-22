package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "data")
    private byte[] data;
}
