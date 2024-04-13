package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;

    public AdDto getAllAds() {
        return null;
    }

    @Override
    public AdDto createAd(CreateOrUpdateAdDto adDto, MultipartFile image, Authentication authentication) throws IOException {
        return null;
    }

    @Override
    public AdsDto getMyAds(Authentication authentication) {
        return null;
    }

    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        return null;
    }

    @Override
    public void deleteAd(Integer id, Authentication authentication) {

    }

    @Override
    public ExtendedAdDto findExtendedAd(Integer id) {
        return null;
    }

    @Override
    public byte[] updateImageAd(Integer id, MultipartFile image) throws IOException {
        return null;
    }

    @Override
    public byte[] getImage(Integer imageId) throws IOException {
        return null;
    }
}
