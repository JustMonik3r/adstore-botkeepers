package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {
    CommentsDto getComments(Integer id);

    CommentDto createComment(Integer id,
                             CreateOrUpdateComment createCommentDto);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComment(Integer id, Integer commentId,
                             CreateOrUpdateComment updateCommentDto);
}
