package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDtoWithProducts;

@Component
@RequiredArgsConstructor
public class SubcategoryDtoWithProductsUtil {
    private final ProductDtoUtil productDtoUtil;
    public SubcategoryDtoWithProducts toDto(Subcategory entityModel) {
        SubcategoryDtoWithProducts dto = new SubcategoryDtoWithProducts();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setProducts(productDtoUtil.toDtoList(entityModel.getProducts()));
        return dto;
    }
}
