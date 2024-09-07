package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;
import ru.etu.merchstoreLETI.web.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDtoUtil  implements EntityDtoUtil<CommentDto, Comment> {
    @Override
    public CommentDto toDto(Comment entityModel) {
        CommentDto dto = new CommentDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  Comment toEntity(CommentDto entityModelDto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(entityModelDto,comment);
        return comment;
    }

    @Override
    public List<CommentDto> toDtoList(List<Comment> entityModelList) {
        List<CommentDto> commentModelDtos = new ArrayList<>();
        for(Comment model : entityModelList){
            CommentDto dto = toDto(model);
            commentModelDtos.add(dto);
        }
        return commentModelDtos;
    }

    @Override
    public List<Comment> toEntityList(List<CommentDto> entityModelDtoList) {
        List<Comment> commentModels = new ArrayList<>();
        for(CommentDto model : entityModelDtoList){
            Comment modelAdd = toEntity(model);
            commentModels.add(modelAdd);
        }
        return commentModels;
    }
}

