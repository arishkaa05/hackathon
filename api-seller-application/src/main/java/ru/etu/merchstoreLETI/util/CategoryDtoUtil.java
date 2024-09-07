package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;
import ru.etu.merchstoreLETI.web.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryDtoUtil implements EntityDtoUtil<CategoryDto, Category> {
    @Override
    public CategoryDto toDto(Category entityModel) {
        CategoryDto dto = new CategoryDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  Category toEntity(CategoryDto entityModelDto) {
        Category category = new Category();
        BeanUtils.copyProperties(entityModelDto,category);
        return category;
    }

    @Override
    public List<CategoryDto> toDtoList(List<Category> entityModelList) {
        List<CategoryDto> categoryModelDtos = new ArrayList<>();
        for(Category model : entityModelList){
            CategoryDto dto = toDto(model);
            categoryModelDtos.add(dto);
        }
        return categoryModelDtos;
    }

    @Override
    public List<Category> toEntityList(List<CategoryDto> entityModelDtoList) {
        List<Category> categoryModels = new ArrayList<>();
        for(CategoryDto model : entityModelDtoList){
            Category modelAdd = toEntity(model);
            categoryModels.add(modelAdd);
        }
        return categoryModels;
    }
}

