package ru.skypro.homework.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateCommentDto {

    //private Integer pk;

    private String text;

   // private LocalDateTime createdAt;

}
