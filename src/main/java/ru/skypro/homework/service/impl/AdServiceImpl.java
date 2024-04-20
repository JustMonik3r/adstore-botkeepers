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
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.mappers.AdMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    //@Value("${image.dir.path}")
    //private String imagesDir;

    private String objectAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    //Получение всех объявлений
    public AdsDto getAllAds() {
        List<AdDto> collect = adRepository.findAll().stream().map(adMapper::adsToDto).collect(Collectors.toList());
        return new AdsDto(collect.size(), collect);
    }

    //    Добавление объявления
    public CreateOrUpdateAdDto createAd(Authentication authentication, CreateOrUpdateAdDto createAd, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        Ad ad = new Ad();
        ad.setTitle(createAd.getTitle());
        ad.setPrice(createAd.getPrice());
        ad.setDescription(createAd.getDescription());
        ad.setAuthor(user);
        adRepository.save(ad);

        Path filePath = Path.of("./image",  "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = new Image();
        imageRepository.findById(image.getId()).orElseGet(Image::new);
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);

        ad.setImages(image);
        adRepository.save(ad);
        return adMapper.updateAdToDto(ad);

    }

    //Получение информации об объявлении
    public ExtendedAdDto getAdById(Integer id) {
        return adRepository.findById(id).map(adMapper::extendAdToDto).orElseThrow(() -> new AdNotFoundException("Объявление не найдено."));
    }

    //Удаление объявления
    public void deleteAdById(Integer id) {
        adRepository.deleteById(id);
    }

    //Обновление информации об объявлении
    public CreateOrUpdateAdDto updateAdById(Integer id, CreateOrUpdateAdDto updateAd) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Объявление не найдено."));
        ad.setTitle(updateAd.getTitle());
        ad.setPrice(updateAd.getPrice());
        ad.setDescription(updateAd.getDescription());
        adRepository.save(ad);
        return adMapper.updateAdToDto(ad);
    }

    //Получение объявлений авторизованного пользователь
    public AdsDto getMyAds(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        List<AdDto> collect = adRepository.findByAuthorId(user.getId()).stream().map(e -> {
            AdDto adDto = new AdDto();
            adDto.setAuthor(e.getAuthor().getId());
            adDto.setPk(e.getId());
            adDto.setImage(e.getId() + "/image");
            adDto.setTitle(e.getTitle());
            adDto.setPrice(e.getPrice());
            return adDto;
        }).collect(Collectors.toList());
        return new AdsDto(collect.size(),collect);
    }

    //     Обновление картинки объявления
    public void updateImage(Integer adId, MultipartFile file) throws IOException {
        Ad ad = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Объявление не найдено."));
        Path filePath = Path.of("./image", adId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = imageRepository.findById(adId).orElseGet(Image::new);
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);

        ad.setImages(image);
        adRepository.save(ad);
    }


    //имя файла расширяется
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    //находит объявление по идентификатору
    public Optional<Ad> findOne(Integer id) {
        return adRepository.findById(id);
    }

    //находит объявление по идентификатору
    public Ad findAd(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Объявление не найдено."));
    }
}
