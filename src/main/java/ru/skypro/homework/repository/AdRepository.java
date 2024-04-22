package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.html.HTMLDocument;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;

import java.awt.color.ICC_Profile;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    Optional<Ad> findByUsersId(Integer id);

    Ad findAdByImages(Integer id);
}
