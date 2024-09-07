package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.web.dto.ProductDtoWithCategory;

@Component
public class ProductDtoWithCategoriesUtil {
    public ProductDtoWithCategory toDto(Product entityModel) {
        ProductDtoWithCategory dto = new ProductDtoWithCategory();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }
}
