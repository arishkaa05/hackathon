package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.web.dto.BucketDtoWithProducts;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;

@Component
@RequiredArgsConstructor
public class BucketDtoWithProductsUtil {
    private final ProductDtoUtil productDtoUtil;
    public BucketDtoWithProducts toDto(Bucket entityModel) {
        BucketDtoWithProducts dto = new BucketDtoWithProducts();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setUserId(entityModel.getUser().getId());
        dto.setProductDtos( productDtoUtil.toDtoList(entityModel.getProducts()));
        return dto;
    }
}
