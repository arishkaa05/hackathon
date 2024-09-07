package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubcategoryDtoUtil implements EntityDtoUtil<SubcategoryDto, Subcategory> {
    @Override
    public  SubcategoryDto toDto(Subcategory entityModel) {
        SubcategoryDto dto = new SubcategoryDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  Subcategory toEntity(SubcategoryDto entityModelDto) {
        Subcategory subcategory = new Subcategory();
        BeanUtils.copyProperties(entityModelDto,subcategory);
        return subcategory;
    }

    @Override
    public List<SubcategoryDto> toDtoList(List<Subcategory> entityModelList) {
        List<SubcategoryDto> subcategoryModelDtos = new ArrayList<>();
        for(Subcategory model : entityModelList){
            SubcategoryDto dto = toDto(model);
            subcategoryModelDtos.add(dto);
        }
        return subcategoryModelDtos;
    }

    @Override
    public List<Subcategory> toEntityList(List<SubcategoryDto> entityModelDtoList) {
        List<Subcategory> subcategoryModels = new ArrayList<>();
        for(SubcategoryDto model : entityModelDtoList){
            Subcategory modelAdd = toEntity(model);
            subcategoryModels.add(modelAdd);
        }
        return subcategoryModels;
    }
}
