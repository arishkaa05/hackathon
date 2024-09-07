package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.web.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDtoUtil implements EntityDtoUtil<ProductDto, Product> {
    @Override
    public  ProductDto toDto(Product entityModel) {
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  Product toEntity(ProductDto entityModelDto) {
        Product product = new Product();
        BeanUtils.copyProperties(entityModelDto,product);
        return product;
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> entityModelList) {
        List<ProductDto> productModelDtos = new ArrayList<>();
        for(Product model : entityModelList){
            ProductDto dto = toDto(model);
            productModelDtos.add(dto);
        }
        return productModelDtos;
    }

    @Override
    public List<Product> toEntityList(List<ProductDto> entityModelDtoList) {
        List<Product> productModels = new ArrayList<>();
        for(ProductDto model : entityModelDtoList){
            Product modelAdd = toEntity(model);
            productModels.add(modelAdd);
        }
        return productModels;
    }
}

