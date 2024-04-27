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
    @Mapping(target = "createdAt", source = "createAt")
    @Mapping(target = "authorFirstName", expression = "java(comment.getAuthor().getFirstName())" )
    @Mapping(target = "text", source = "text" )
    @Mapping(target = "authorImage", expression = "java(comment.getAuthor().getImages() != null ? \"/avatars/\"+comment.getAuthor().getId() : null)")
    CommentDto commentsToDto(Comment comment);


    @Mapping(target = "text",source = "text")
    CreateOrUpdateCommentDto updateCommentToDto(Comment comment);

}
