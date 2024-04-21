package ru.skypro.homework.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "pk",source = "id")
    @Mapping(target = "author",expression = "java(comment.getAuthor().getId())")
    CommentDto commentsToDto(Comment comment);
    @Mapping(target = "pk",source = "id")
    CreateOrUpdateCommentDto updateCommentToDto(Comment comment);

}
