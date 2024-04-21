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
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CheckRoleService;

import java.io.IOException;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {
    private final AdServiceImpl adService;
    private final AdRepository adRepository;

    @Operation(summary = "Получение всех объявлений")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(summary = "Добавление объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateOrUpdateAdDto createAds,
                                       @RequestPart("image") MultipartFile image,
                                       Authentication authentication) throws IOException {
        if (authentication.getName() != null) {
            adService.createAd(createAds, image, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Получение информации об объявлении")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (adService.findExtendedAd(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(adService.findExtendedAd(id));
        }
    }

    @Operation(summary = "Удаление объявления")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userEmail = adRepository.findAdByPk(id).getUser().getEmail();
        boolean isAdminOrOwner = CheckRoleService.isAdminOrOwnerAd(authentication, userEmail);
        ExtendedAdDto foundAd = adService.findExtendedAd(id);

        if (!isAdminOrOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (foundAd == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        adService.deleteAd(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable Integer id,
                                           @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                           Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userEmail = adRepository.findAdByPk(id).getUser().getEmail();
        boolean isAdminOrOwner = CheckRoleService.isAdminOrOwnerAd(authentication, userEmail);
        ExtendedAdDto foundAd = adService.findExtendedAd(id);

        if (!isAdminOrOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (foundAd == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        adService.updateAds(id, createOrUpdateAdDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Получение объявления пользователя")
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        if (authentication.getName() != null) {
            return new ResponseEntity<>(adService.getMyAds(authentication), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Обновление картинки объявления")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image,
                                              Authentication authentication) throws IOException {
        String userEmail = adRepository.findAdByPk(id).getUser().getEmail();
        boolean isAdminOrOwner = CheckRoleService.isAdminOrOwnerAd(authentication, userEmail);
        if (authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!isAdminOrOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (adService.updateImageAd(id, image) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(adService.updateImageAd(id, image));
        }
    }
}