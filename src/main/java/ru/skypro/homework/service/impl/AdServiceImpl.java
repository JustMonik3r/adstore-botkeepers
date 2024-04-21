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
import ru.skypro.homework.dto.mapper.AdMapper;
import ru.skypro.homework.service.mappers.CreateOrUpdateAdMapper;
import ru.skypro.homework.service.mappers.ExtendedAdMapper;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.AccessErrorException;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final CreateOrUpdateAdMapper createOrUpdateAdMapper;

    /**
     Retrieves all ads.
     @return The DTO containing the list of all ads.
     */
    public AdsDto getAllAds() {
        List<AdDto> adList = adRepository.findAll().stream()
                .map(AdMapper::adToAdDto)
                .collect(Collectors.toList());
        return new AdsDto(adList.size(), adList);
    }

    /**

     Creates a new ad.
     @param adDto The DTO containing the ad data.
     @param image The image file associated with the ad.
     @param authentication The authentication information of the user creating the ad.
     @return The DTO representing the created ad.
     @throws IOException If an error occurs while reading the image file.
     */
    @Override
    public AdDto createAd(CreateOrUpdateAdDto adDto, MultipartFile image, Authentication authentication) throws IOException {
        User user = userService.getUser(authentication.getName());
        Ad ad = createOrUpdateAdMapper.mapToAd(adDto);
        ad.setUser(user);
        ad.setData(image.getBytes());
        ad.setImageUrl(image.getOriginalFilename());
        return AdMapper.adToAdDto(adRepository.save(ad));
    }

    /**
     Retrieves all ads created by the authenticated user.
     @param authentication The authentication information of the user.
     @return The DTO containing the list of all ads created by the user.
     */
    @Override
    public AdsDto getMyAds(Authentication authentication) {
        Integer userId = userService.findByEmail(authentication.getName()).getId();
        List<AdDto> allMyAds = adRepository.findAll()
                .stream()
                .filter(ad -> ad.getUser().getId().equals(userId))
                .map(AdMapper::adToAdDto)
                .collect(Collectors.toList());
        return new AdsDto(allMyAds.size(), allMyAds);
    }
    /**
     Updates or creates the details of an ad.
     @param id The ID of the ad to update.
     @param createOrUpdateAdDto The DTO containing the updated ad data.
     @return The DTO representing the updated ad.
     */
    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = adRepository.findAdById(id);
        if (ad == null) {
            return null;
        }
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        adRepository.save(ad);
        return AdMapper.adToAdDto(ad);
    }
    /**
     Deletes an ad.
     @param id The ID of the ad to delete.
     @param authentication The authentication information of the user attempting to delete the ad.
     @throws AccessErrorException If the user is not authorized to delete the ad.
     */
    @Override
    public void deleteAd(Integer id, Authentication authentication) {
        Ad deletedAd = adRepository.findAdById(id);
        if (CheckRoleService.isAdminOrOwnerAd(authentication, deletedAd.getUser().getEmail())) {
            adRepository.deleteById(id);
        } else {
            throw new AccessErrorException();
        }
    }
    /**
     Retrieves an extended version of an ad.
     @param id The ID of the ad to retrieve.
     @return The DTO representing the extended ad.
     */
    @Override
    public ExtendedAdDto findExtendedAd(Integer id) {
        Ad ad = adRepository.findAdById(id);
        if (ad != null) {
            return ExtendedAdMapper.toDto(ad);
        }
        return null;
    }
    /**
     Updates the image of an ad.
     @param id The ID of the ad to update.
     @param image The new image file for the ad.
     @return The byte array representing the updated image data.
     @throws IOException If an error occurs while reading the image file.
     */
    @Override
    public byte[] updateImageAd(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Объявление не найдено."));
        ad.setData(image.getBytes());
        ad.setImageUrl("/images/" + ad.getId());
        adRepository.save(ad);
        return ad.getData();
    }
    /**
     Retrieves the image data for an ad.
     @param imageId The ID of the ad image to retrieve.
     @return The byte array representing the image data.
     @throws IOException If an error occurs while retrieving the image data.
     */
    @Override
    public byte[] getImage(Integer imageId) throws IOException {
        return adRepository.findAdById(imageId).getData();
    }
}