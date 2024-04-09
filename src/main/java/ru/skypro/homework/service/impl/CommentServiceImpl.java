package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mappers.CommentMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    /*private final UserRepository userRepository;
    private final AdRepository adRepository;*/

    /**
     * Получает комментарии к объявлению.
     * @param id - id of the ad
     * @return Объект CommentsDto, содержащий список комментариев.
     */
    public CommentsDto getComments(Integer id) {
        List<Comment> comment = commentRepository.findByAdPk(id);
        if(comment == null) {
            return null;
        }
        List<CommentDto> commentList = comment.stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
        return new CommentsDto(commentList.size(), commentList);
    }

    public CommentDto createComment(Integer id,
                                    CreateOrUpdateCommentDto createCommentDto){
        Timestamp localDateTime = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        Comment comment = new Comment();
        Ad ad = adRepository.findAdByPk(id);
        if(ad == null) {
            return null;
        }
        comment.setText(createCommentDto.getText());
        comment.setCreatedAt(localDateTime);
        comment.setAd(ad);
        commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId);
        commentRepository.delete(comment);
    }

    public CommentDto updateComment (Integer adId, Integer commentId,CreateOrUpdateCommentDto updateCommentDto){
        Comment comment = commentRepository.findById(commentId);
        if (comment != null) {
            comment.setText(updateCommentDto.getText());
            commentRepository.save(comment);
            return commentMapper.commentToCommentDto(comment);
        }
        return null;
    }
}

