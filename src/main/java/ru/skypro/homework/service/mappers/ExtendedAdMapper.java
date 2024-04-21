package ru.skypro.homework.service.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

@Component
public class ExtendedAdMapper {

    public static ExtendedAdDto toDto(Ad entity) {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(entity.getPk());
        dto.setAuthorFirstName(entity.getUser().getFirstName());
        dto.setAuthorLastName(entity.getUser().getLastName());
        dto.setDescription(entity.getDescription());
        dto.setEmail(entity.getUser().getEmail());
        dto.setPhone(entity.getUser().getPhone());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        dto.setImage("/images/" + entity.getPk());
        return dto;
    }
}