package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.web.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper extends Mappable<Comment, CommentDto> {
}
