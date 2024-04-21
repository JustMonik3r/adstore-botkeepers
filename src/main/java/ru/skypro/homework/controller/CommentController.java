package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.Optional;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag( name = "Комментарии")
public class CommentController {
    private CommentService commentService;
    private AdService adService;


    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer id) {
        CommentsDto comments = commentService.getComments(id);
        if (comments == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comments);
    }


    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<CreateOrUpdateCommentDto> createComment(@PathVariable Integer id, @RequestBody CreateOrUpdateCommentDto createCommentDto, Authentication authentication) {
        Optional<Ad> ad = adService.findOne(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CreateOrUpdateCommentDto commentDto = commentService.createComment(id, createCommentDto, authentication);
        return ResponseEntity.ok(commentDto);
    }


    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId) {
        Optional<Ad> ad = adService.findOne(adId);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Обновление комментария")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateCommentDto> updateComment (@PathVariable Integer adId,
                                                                   @PathVariable Integer commentId,@RequestBody CreateOrUpdateCommentDto updateComment){
        CreateOrUpdateCommentDto comment =  commentService.updateComment(adId, commentId, updateComment);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(comment);
    }

}
