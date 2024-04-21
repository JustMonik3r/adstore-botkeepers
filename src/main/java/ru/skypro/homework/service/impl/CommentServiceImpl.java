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
    private final LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter dateAndTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Получает комментарии к объявлению.
     * @param id - id of the ad
     * @return Объект CommentsDto, содержащий список комментариев.
     */
    public CommentsDto getComments(Integer id) {
        List<Comment> comments = commentRepository.findByAdPk(id);
        List<CommentDto> collect = comments.stream().map(e -> commentMapper.commentsToDto(e)).collect(Collectors.toList());
        CommentsDto commentsDto = new CommentsDto(collect.size(), collect);
        return commentsDto;
    }

    public CreateOrUpdateCommentDto createComment(Integer id, CreateOrUpdateCommentDto createCommentDto, Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).get();
        Ad ad = adRepository.findById(id).get();
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setCreateAt(LocalDateTime.parse(today.format(dateAndTime)));
        comment.setAds(ad);
        comment.setText(createCommentDto.getText());
        commentRepository.save(comment);
        return commentMapper.updateCommentToDto(comment);
    }

    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow();
        commentRepository.delete(comment);
    }

    public CreateOrUpdateCommentDto updateComment (Integer adId, Integer commentId,CreateOrUpdateCommentDto updateCommentDto){
        Comment comment = commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow();
        comment.setText(updateCommentDto.getText());
        Comment save = commentRepository.save(comment);
        return commentMapper.updateCommentToDto(save);
    }
}

