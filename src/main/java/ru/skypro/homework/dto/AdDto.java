package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Ad {
    private Integer pk;
    private String title;
    private Integer author;
    private Integer price;
    private String image;
}
