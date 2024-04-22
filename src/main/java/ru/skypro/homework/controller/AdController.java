package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {
    private final AdService adService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto ads = adService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CreateOrUpdateAdDto> createAd(Authentication authentication, @RequestPart(value = "properties", required = true) CreateOrUpdateAdDto properties,
                                           @RequestPart(value = "image", required = true) MultipartFile image) throws IOException {
        CreateOrUpdateAdDto createAd = adService.createAd(authentication, properties, image);
        return ResponseEntity.ok(createAd);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExtendedAdDto> getAdById(@PathVariable Integer id) {
        ExtendedAdDto extendedAd = adService.getAdById(id);
        if (extendedAd == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdDto> deleteAdById(@PathVariable Integer id) {
        Optional<Ad> ad = adService.findOne(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        adService.deleteAdById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<CreateOrUpdateAdDto> updateAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto updateAd) {
        CreateOrUpdateAdDto foundAd = adService.updateAdById(id, updateAd);
        if (foundAd == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundAd);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getMyAds (Authentication authentication) {
        AdsDto ads = adService.getMyAds(authentication);
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> updateImage(@PathVariable Integer id, @RequestParam MultipartFile image) throws
            IOException {
        Optional<Ad> adEntity = adService.findOne(id);
        if (adEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        adService.updateImage(id, image);
        return ResponseEntity.ok().build();
    }
}
