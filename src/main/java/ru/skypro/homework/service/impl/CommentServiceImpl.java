package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mappers.CommentMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    DateTimeFormatter dateAndTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Retrieves the comments for an ad
     * @param id - id of the ad
     * @return The CommentsDto object containing the list of comments
     */
    public CommentsDto getComments(Integer id) {
        List<Comment> comments = adRepository.findById(id).orElseThrow().getComments();
        List<CommentDto> collect = comments.stream().map(commentMapper::commentsToDto).collect(Collectors.toList());
        return new CommentsDto(collect.size(), collect);
    }

    /**
     * Creates a new comment for an ad.
     * @param id - id of the ad.
     * @param createCommentDto - the CreateOrUpdateCommentDto object containing the comment details
     * @param authentication - the authentication object representing the current user
     * @return the CommentDto object representing the created comment
     */
    public CommentDto createComment(Integer id, CreateOrUpdateCommentDto createCommentDto, Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Ad ad = adRepository.findById(id).orElseThrow();
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setCreateAt(LocalDateTime.parse(LocalDateTime.now().format(dateAndTime)));
        comment.setAds(ad);
        comment.setText(createCommentDto.getText());
        commentRepository.save(comment);
        return commentMapper.commentsToDto(comment);
    }

    /**
     * Deletes a comment
     * @param adId - id of the ad.
     * @param commentId - id of the comment
     * @param authentication - the authentication object representing the current user
     */
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        Comment comment = commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow();
        commentRepository.delete(comment);
    }

    /**
     * Updates a comment
     * @param adId - id of the ad
     * @param commentId - id of the comment
     * @param updateCommentDto - the CreateOrUpdateCommentDto object containing the updated comment details
     * @param authentication - - the authentication object representing the current user
     * @return the CreateOrUpdateCommentDto object representing the updated comment.
     */
    public CreateOrUpdateCommentDto updateComment (Integer adId, Integer commentId,CreateOrUpdateCommentDto updateCommentDto, Authentication authentication){
        Comment comment = commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow();
        comment.setText(updateCommentDto.getText());
        Comment save = commentRepository.save(comment);
        return commentMapper.updateCommentToDto(save);
    }
}

