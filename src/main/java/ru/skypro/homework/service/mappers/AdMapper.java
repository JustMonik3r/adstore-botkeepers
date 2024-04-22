package ru.skypro.homework.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "pk",source = "id")
    @Mapping(target = "author",expression = "java(ad.getUsers().getId())")
    AdDto adsToDto(Ad ad);

    ExtendedAdDto extendAdToDto(Ad adEntity);

    CreateOrUpdateAdDto updateAdToDto(Ad ad);
}
