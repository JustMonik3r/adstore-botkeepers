package ru.skypro.homework.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Timestamp createdAt;
    private Integer pk;
    private String text;
}
