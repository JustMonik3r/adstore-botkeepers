package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {
//    private CommentService commentService;

//
//    @Operation(summary = "Получение комментариев объявления",
//            responses = {
//                    @ApiResponse(responseCode = "200", content = @Content),
//                    @ApiResponse(responseCode = "401", content = @Content),
//                    @ApiResponse(responseCode = "404", content = @Content)})
//    @GetMapping("/{id}/comments")
//    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(commentService.getComments(id));
//    }
//
//    @Operation(summary = "Добавление комментария к объявлению",
//            responses = {
//                    @ApiResponse(responseCode = "200", content = @Content),
//                    @ApiResponse(responseCode = "401", content = @Content),
//                    @ApiResponse(responseCode = "404", content = @Content)})
//    @PostMapping("/{id}/comments")
//    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id,
//                                                 @RequestBody CreateOrUpdateCommentDto createCommentDto) {
//        return new ResponseEntity<>(commentService.createComment(id, createCommentDto), HttpStatus.CREATED);
//    }
//
//    @Operation(summary = "Удаление комментария",
//            responses = {
//                    @ApiResponse(responseCode = "200", content = @Content),
//                    @ApiResponse(responseCode = "401", content = @Content),
//                    @ApiResponse(responseCode = "403", content = @Content),
//                    @ApiResponse(responseCode = "404", content = @Content)})
//    @DeleteMapping("/{adId}/comments/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId,
//                                              @PathVariable("commentId") Integer commentId) {
//
//        commentService.deleteComment(adId, commentId);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Обновление комментария",
//            responses = {
//                    @ApiResponse(responseCode = "200", content = @Content),
//                    @ApiResponse(responseCode = "401", content = @Content),
//                    @ApiResponse(responseCode = "403", content = @Content),
//                    @ApiResponse(responseCode = "404", content = @Content)})
//    @PatchMapping("/{adId}/comments/{commentId}")
//    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId,
//                                                    @PathVariable("commentId") Integer commentId,
//                                                    @RequestBody CreateOrUpdateCommentDto updateCommentDto) {
//        return ResponseEntity.ok(commentService.updateComment(adId, commentId, updateCommentDto));
//    }
}
