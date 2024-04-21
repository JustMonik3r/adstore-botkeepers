package ru.skypro.homework.service.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
@Component
public class CreateOrUpdateAdMapper {

    public CreateOrUpdateAdDto mapToCreateOrUpdateAdDto(Ad entity) {
        CreateOrUpdateAdDto dto = new CreateOrUpdateAdDto();
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public Ad mapToAd(CreateOrUpdateAdDto dto) {
        Ad entity = new Ad();
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}