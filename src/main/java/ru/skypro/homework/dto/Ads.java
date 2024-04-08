package ru.skypro.homework.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ads {

    private Integer count;

    private Collection<Ad> results;
}