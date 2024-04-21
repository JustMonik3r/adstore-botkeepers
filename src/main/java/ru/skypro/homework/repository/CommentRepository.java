package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "select * from comments where id=:commentId and ads_id=:adId" , nativeQuery = true)
    Optional<Comment> findByIdAndAdsId(Integer commentId,Integer adId);

    List<Comment> findByAdsId(Integer adId);
}
