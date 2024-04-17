package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

import java.io.IOException;

public interface AdService {

    AdDto createAd (CreateOrUpdateAdDto adDto, MultipartFile image, Authentication authentication) throws IOException;

    AdsDto getMyAds (Authentication authentication);

    AdDto updateAds (Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

    void deleteAd (Integer id, Authentication authentication);

    ExtendedAdDto findExtendedAd (Integer id);

    byte[]updateImageAd (Integer id, MultipartFile image) throws IOException;

    byte[] getImage (Integer imageId) throws IOException;
  
}
