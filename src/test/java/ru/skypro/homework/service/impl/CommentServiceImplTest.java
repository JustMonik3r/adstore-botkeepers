package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.mappers.CommentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentServiceImpl commentService;


    //В этом тесте мы мокируем зависимости AdRepository и CommentMapper,
    // создаем объекты Ad и Comment, и проверяем метод getComments, убеждаясь,
    // что он возвращает ожидаемый результат на основе мокированных данных.
    @Test
    void testGetComments() {
        // Arrange
        Ad ad = new Ad();
        ad.setId(1);
        List<Comment> comments = new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setText("Comment 1");
        comments.add(comment1);
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setText("Comment 2");

        comments.add(comment2);
        ad.setComments(comments);

        when(adRepository.findById(1)).thenReturn(Optional.of(ad));

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setPk(1);
        commentDto1.setText("Comment 1");
        CommentDto commentDto2 = new CommentDto();
        commentDto2.setPk(2);
        commentDto2.setText("Comment 2");

        when(commentMapper.commentsToDto(comment1)).thenReturn(commentDto1);
        when(commentMapper.commentsToDto(comment2)).thenReturn(commentDto2);

        // Act
        CommentsDto commentsDto = commentService.getComments(1);

        // Assert
        assertEquals(2, commentsDto.getCount());
        assertEquals("Comment 1", commentsDto.getResults().get(0).getText());
        assertEquals("Comment 2", commentsDto.getResults().get(1).getText());
    }

    // Тест проверяет корректность создания комментария через сервис commentService.
    @Test
    public void testCreateComment() {
        // Given
        CreateOrUpdateCommentDto createCommentDto = new CreateOrUpdateCommentDto();
        createCommentDto.setText("Test comment");

        User user = new User();
        user.setId(1);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Ad ad = new Ad();
        ad.setId(1);
        when(adRepository.findById(anyInt())).thenReturn(Optional.of(ad));

        Comment savedComment = new Comment();
        savedComment.setId(1);
        when(commentRepository.save(any())).thenReturn(savedComment);

        CommentDto commentDto = new CommentDto();
        when(commentMapper.commentsToDto(any())).thenReturn(commentDto);

        // When
        CommentDto result = commentService.createComment(1, createCommentDto, authentication);

        // Then
        assertEquals(savedComment.getText(), result.getText());
    }

    // Тест проверяет корректность удаления комментария через сервис commentService.

    @Test
    public void testDeleteComment() {
        // Given
        Integer adId = 1;
        Integer commentId = 1;

        Ad ad = new Ad();
        ad.setId(adId);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setId(adId);
        when(commentRepository.findByIdAndAdsId(anyInt(), anyInt())).thenReturn(java.util.Optional.of(comment));

        // When
        commentService.deleteComment(adId, commentId, authentication);

        // Then
        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
    }

    // Тест проверяет корректность обновления текста комментария через сервис commentService.
    @Test
    public void testUpdateComment() {
        // Given
        Integer adId = 1;
        Integer commentId = 1;

        CreateOrUpdateCommentDto updateCommentDto = new CreateOrUpdateCommentDto();
        updateCommentDto.setText("Updated text");

        Ad ad = new Ad();
        ad.setId(adId);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setId(adId);
        when(commentRepository.findByIdAndAdsId(anyInt(), anyInt())).thenReturn(Optional.of(comment));

        Comment updatedComment = new Comment();
        updatedComment.setId(commentId);
        updatedComment.setId(adId);
        updatedComment.setText("Updated text");
        when(commentRepository.save(comment)).thenReturn(updatedComment);

        // When
        commentService.updateComment(adId, commentId, updateCommentDto, Mockito.mock(Authentication.class));

        // Then
        Mockito.verify(commentRepository, Mockito.times(1)).findByIdAndAdsId(commentId, adId);
        Mockito.verify(commentRepository, Mockito.times(1)).save(comment);
    }
}