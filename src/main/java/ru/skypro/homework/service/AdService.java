package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

import java.io.IOException;
import java.util.Optional;

public interface AdService {
    CreateOrUpdateAdDto createAd(Authentication authentication, CreateOrUpdateAdDto createAd, MultipartFile file) throws IOException;

    ExtendedAdDto getAdById(Integer id);

    CreateOrUpdateAdDto updateAdById(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

    void deleteAdById(Integer id);

    AdsDto getMyAds(Authentication authentication);

    void updateImage(Integer adId, MultipartFile file) throws IOException;

    AdsDto getAllAds();

    Optional<Ad> findOne(Integer id);

    Ad findAd(Integer id);
}
