package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.web.dto.BucketDtoWithProducts;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;
import ru.etu.merchstoreLETI.web.dto.CommentDto;
import ru.etu.merchstoreLETI.web.dto.CommentDtoWithUsername;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDtoWithUsernameUtil {
    public CommentDtoWithUsername toDto(Comment entityModel) {
        CommentDtoWithUsername dto = new CommentDtoWithUsername();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setUsername(entityModel.getUser().getUsername());
        dto.setUserId(entityModel.getUser().getId());
        dto.setCreateDate(entityModel.getCreateDate());
        dto.setProductId(entityModel.getProduct().getId());
        dto.setImageUrl(entityModel.getUser().getImageUrl());
        return dto;
    }
    public List<CommentDtoWithUsername> toDtoList(List<Comment> entityModel) {
        List<CommentDtoWithUsername> commentModelDtos = new ArrayList<>();
        for(Comment model : entityModel){
            CommentDtoWithUsername dto = toDto(model);
            commentModelDtos.add(dto);
        }
        return commentModelDtos;
    }
}
