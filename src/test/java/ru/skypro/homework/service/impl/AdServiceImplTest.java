package ru.skypro.homework.service.impl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.mappers.AdMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdServiceImpl adService;

    @Mock
    private AdMapper adMapper;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private UserRepository userRepository;



    @Test
    void testGetAllAds() {
        // Arrange
        Ad ad1 = new Ad();
        ad1.setId(1);
        ad1.setTitle("Ad 1");


        Ad ad2 = new Ad();
        ad2.setId(2);
        ad2.setTitle("Ad 2");

        List<Ad> ads = Arrays.asList(ad1, ad2);

        when(adRepository.findAll()).thenReturn(ads);

        // Act
        AdsDto result = adService.getAllAds();

        // Assert
        assertEquals(2, result.getCount());
        assertEquals(2, result.getResults().size());
//        assertEquals("Ad 1", result.getAds().get(0).getTitle());
//        assertEquals("Ad 2", result.getAds().get(1).getTitle());
    }

    //  В этом тесте мы мокируем зависимости AdRepository, AdMapper, ImageRepository и UserRepository,
    //  а также создаем мок объект Authentication и MultipartFile.
    //  Затем мы проверяем метод createAd, убеждаясь,
    //  что он вызывает необходимые методы сохранения в репозиториях и возвращает ожидаемый результат.
    @Test
    void testCreateAd() throws IOException {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        CreateOrUpdateAdDto createAdDto = new CreateOrUpdateAdDto();
        createAdDto.setTitle("Test Ad");
        createAdDto.setPrice(100);
        createAdDto.setDescription("Test Description");

        MultipartFile file = mock(MultipartFile.class);
        when(file.getSize()).thenReturn(100L);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn(new byte[0]);

        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));

        Ad ad = new Ad();
        when(adMapper.updateAdToDto(any())).thenReturn(createAdDto);

        // Act
        CreateOrUpdateAdDto createdAd = adService.createAd(authentication, createAdDto, file);

        // Assert
        assertEquals(createAdDto, createdAd);
        verify(adRepository, times(2)).save(any(Ad.class));
        verify(imageRepository, times(1)).save(any(Image.class));
    }


    // В этом тесте мы используем Mockito для создания макетов (mocks) репозитория AdRepository и
    // маппера AdMapper. Мы также используем аннотацию @InjectMocks
    // для создания экземпляра AdServiceImpl и автоматического внедрения макетов.
    // Мы затем настраиваем поведение макетов с помощью метода when и
    // проверяем результат с помощью метода assertEquals.
    @Test
    void testGetAdById() {
        // Arrange
        Integer adId = 1;
        Ad ad = new Ad();
        ad.setId(adId);
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        extendedAdDto.setPk(adId);

        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(adMapper.extendAdToDto(ad)).thenReturn(extendedAdDto);

        // Act
        ExtendedAdDto result = adService.getAdById(adId);

        // Assert
        assertEquals(adId, result.getPk());
    }

    @Test
    void deleteAdById() {
    }

    @Test
    void updateAdById() {
    }

    @Test
    void getMyAds() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void findOne() {
    }

    @Test
    void findAd() {
    }

    @Test
    void getImage() {
    }
}