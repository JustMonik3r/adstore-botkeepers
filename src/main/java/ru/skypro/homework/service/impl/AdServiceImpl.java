package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.mappers.AdMapper;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Value("${image.dir.path}")
    private String imagesDir;

    private String objectAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    /**
     * Retrieves all ads.
     * @return The DTO containing the list of all ads
     */
    public AdsDto getAllAds() {
        List<AdDto> collect = adRepository.findAll().stream().map(adMapper::adsToDto).collect(Collectors.toList());
        return new AdsDto(collect.size(), collect);
    }

    /**
     * Creates a new ad
     * @param authentication The authentication information of the user creating the ad
     * @param createAd The DTO containing the ad data
     * @param file The image file associated with the ad
     * @return The DTO representing the created ad
     * @throws IOException IOException If an error occurs while reading the image file.
     */
    public CreateOrUpdateAdDto createAd(Authentication authentication, CreateOrUpdateAdDto createAd, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(authentication.getName()).get();
        Ad ad = new Ad();
        ad.setTitle(createAd.getTitle());
        ad.setPrice(createAd.getPrice());
        ad.setDescription(createAd.getDescription());
        ad.setUsers(user);
        adRepository.save(ad);

        Image image = new Image();
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);

        ad.setImages(image);
        adRepository.save(ad);
        return adMapper.updateAdToDto(ad);
    }

    /**
     * Retrieves an extended version of an ad
     * @param id - The ID of the ad to retrieve
     * @return - The DTO representing the extended ad
     */
    public ExtendedAdDto getAdById(Integer id) {
        return adRepository.findById(id).map(adMapper::extendAdToDto).orElse(null);
    }

    /**
     * Deletes an ad
     * @param id - The ID of the ad to delete
     */
    public void deleteAdById(Integer id) {
        adRepository.deleteById(id);
    }

    /**
     * Updates or creates the details of an ad
     * @param id - The ID of the ad to update
     * @param updateAd - The DTO containing the updated ad data
     * @return The DTO representing the updated ad
     */
    public CreateOrUpdateAdDto updateAdById(Integer id, CreateOrUpdateAdDto updateAd) {
        Ad adEntity = adRepository.findById(id).get();
        adEntity.setTitle(updateAd.getTitle());
        adEntity.setPrice(updateAd.getPrice());
        adEntity.setDescription(updateAd.getDescription());
        adRepository.save(adEntity);
        return adMapper.updateAdToDto(adEntity);
    }

    /**
     * Retrieves all ads created by the authenticated user
     * @param authentication The authentication information of the user
     * @return The DTO containing the list of all ads created by the user
     */
    public AdsDto getMyAds(Authentication authentication) {
        User userEntity = userRepository.findByEmail(authentication.getName()).get();
        List<AdDto> collect = userEntity.getAdEntityList().stream().map(adMapper::adsToDto).collect(Collectors.toList());
        return new AdsDto(collect.size(),collect);
    }

    /**
     * Updates the image of an ad.
     * @param adId - The ID of the ad to update
     * @param file - The new image file for the ad
     * @throws IOException If an error occurs while reading the image file
     */
    public void updateImage(Integer adId, MultipartFile file) throws IOException {
        Ad ad = findAd(adId);

        Image image = ad.getImages();
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);

        ad.setImages(image);
        adRepository.save(ad);
    }

    /**
     * Retrieves ad by id
     * @param id - The ID of the ad to retrieve
     * @return Optional<Ad> containing the ad
     */
    public Optional<Ad> findOne(Integer id) {
        return adRepository.findById(id);
    }

    /**
     * Retrieves ad by id
     * @param id - The ID of the ad to retrieve
     * @return The object containing the ad
     */
    public Ad findAd(Integer id) {
        return adRepository.findById(id).get();
    }

    /**
     * Retrieves the image data for an ad
     * @param id - The ID of the ad image to retrieve
     * @return The byte array representing the image data
     */
    @Override
    public byte[] getImage(Integer id) {
        return adRepository.findById(id).map(Ad::getImages).map(Image::getData).orElse(null);
    }
}
