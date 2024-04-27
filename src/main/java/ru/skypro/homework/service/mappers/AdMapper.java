package ru.skypro.homework.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", expression = "java(ad.getUsers().getId())")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "image", expression = "java(\"/images/\"+ad.getId())")
    AdDto adsToDto(Ad ad);


    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", expression = "java(ad.getUsers().getFirstName())")
    @Mapping(target = "authorLastName", expression = "java(ad.getUsers().getLastName())")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "email", expression = "java(ad.getUsers().getEmail())")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "phone", expression = "java(ad.getUsers().getPhone())")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "image", expression = "java(\"/images/\"+ad.getId())")
    ExtendedAdDto extendAdToDto(Ad ad);


    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    CreateOrUpdateAdDto updateAdToDto(Ad ad);
}


