package ru.practicum.shareit.comment;

import lombok.NoArgsConstructor;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.NewCommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.Instant;

@NoArgsConstructor
public class CommentMapper {
    public static Comment mapToNewComment(NewCommentDto newCommentDto, Item item, User user) {
        return Comment.builder()
                .text(newCommentDto.getText())
                .created(Instant.now())
                .item(item)
                .author(user)
                .build();
    }

    public static CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .item(comment.getItem())
                .created(comment.getCreated())
                .build();
    }
}
