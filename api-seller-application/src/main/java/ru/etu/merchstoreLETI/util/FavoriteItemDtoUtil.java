package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.*;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.web.dto.BucketDto;
import ru.etu.merchstoreLETI.web.dto.CommentDto;
import ru.etu.merchstoreLETI.web.dto.FavoriteItemDto;
import ru.etu.merchstoreLETI.web.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteItemDtoUtil implements EntityDtoUtil<FavoriteItemDto, FavoriteItem> {
    private final ProductDtoUtil productDtoUtil;
    private final UserRepository userRepository;
    @Override
    public FavoriteItemDto toDto(FavoriteItem entityModel) {
        FavoriteItemDto dto = new FavoriteItemDto();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setUserId(entityModel.getUser().getId());
        dto.setProductDtoList(productDtoUtil.toDtoList(entityModel.getProducts()));
        return dto;
    }

    @Override
    public FavoriteItem toEntity(FavoriteItemDto entityModelDto) {
        FavoriteItem favoriteItem = new FavoriteItem();
        BeanUtils.copyProperties(entityModelDto,favoriteItem);
        User user = userRepository.findById(entityModelDto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден!"));
        favoriteItem.setUser(user);
        favoriteItem.setProducts(productDtoUtil.toEntityList(entityModelDto.getProductDtoList()));
        return favoriteItem;
    }

    @Override
    public List<FavoriteItemDto> toDtoList(List<FavoriteItem> entityModelList) {
        List<FavoriteItemDto> favoriteModelDtos = new ArrayList<>();
        for(FavoriteItem model : entityModelList){
            FavoriteItemDto dto = toDto(model);
            favoriteModelDtos.add(dto);
        }
        return favoriteModelDtos;
    }

    @Override
    public List<FavoriteItem> toEntityList(List<FavoriteItemDto> entityModelDtoList) {
        List<FavoriteItem> favoriteModels = new ArrayList<>();
        for(FavoriteItemDto model : entityModelDtoList){
            FavoriteItem modelAdd = toEntity(model);
            favoriteModels.add(modelAdd);
        }
        return favoriteModels;
    }
}
