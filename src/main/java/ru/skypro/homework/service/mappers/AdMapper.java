package ru.skypro.homework.service.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;

@Component
public class AdMapper {
    public static Ad adDtoToAd(AdDto adDto) {
        Ad ad = new Ad();
        ad.setPk(adDto.getPk());
        ad.setTitle(adDto.getTitle());
        ad.setImageUrl(adDto.getImage());
        ad.setPrice(adDto.getPrice());
        return ad;
    }

    public static AdDto adToAdDto(Ad entity) {
        AdDto adDto = new AdDto();
        adDto.setPk(entity.getPk());
        adDto.setAuthor(entity.getUser().getId());
        adDto.setTitle(entity.getTitle());
        adDto.setPrice(entity.getPrice());
        adDto.setImage("/images/" + entity.getPk());
        return adDto;
    }
}
