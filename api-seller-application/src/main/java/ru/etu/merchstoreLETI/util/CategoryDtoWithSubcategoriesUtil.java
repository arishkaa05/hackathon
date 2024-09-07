package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.web.dto.CategoryDtoWithSubcategories;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDtoWithSubcategoriesUtil {
    private final SubcategoryDtoUtil subcategoryDtoUtil;
    public CategoryDtoWithSubcategories toDto(Category entityModel) {
        CategoryDtoWithSubcategories dto = new CategoryDtoWithSubcategories();
        List<Subcategory> subcategories = entityModel.getSubcategories();
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        for(Subcategory subcategory: subcategories){
            SubcategoryDto subcategoryDto =subcategoryDtoUtil.toDto(subcategory);
            List<Long> productIdsFind = new ArrayList<>();
            for(Product product: subcategory.getProducts()){
                productIdsFind.add(product.getId());
            }
            subcategoryDto.setProductIds(productIdsFind);
            subcategoryDtoList.add(subcategoryDto);
        }
        dto.setSubcategoryDtos(subcategoryDtoList);
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }
}
