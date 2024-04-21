package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getComments(Integer id);

    CommentDto createComment(Integer id,
                             CreateOrUpdateCommentDto createCommentDto, Authentication authentication);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComment(Integer id, Integer commentId,
                             CreateOrUpdateCommentDto updateCommentDto);
}
