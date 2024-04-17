package ru.skypro.homework.service.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;

//@Component
public interface CommentMapper {
    /*static Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getPk());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setText(commentDto.getText());
        return comment;
    }*/




    /*CommentDto commentToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getId());
        //commentDto.setAuthor(comment.getUser().getId());
       // commentDto.setAuthorImage(comment.getUser().getId());
       // commentDto.setAuthorFirstName(comment.getUser().getFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setText(comment.getText());
        return commentDto;
    }*/

}