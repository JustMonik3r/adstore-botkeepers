package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    //Optional<Ad> findByAuthorId(Integer id);

    Ad findAdById(Integer id);
}
