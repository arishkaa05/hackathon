package ru.etu.merchstoreLETI.service.interf;

import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDtoWithProducts;

import java.util.List;

public interface SubcategoryService extends CRUDService<SubcategoryDto,Long>,PaginationService<SubcategoryDto,Long>,PaginationServiceFindById<SubcategoryDtoWithProducts,Long> {
    List<SubcategoryDto> findByCategoryId(Long categoryId);

    SubcategoryDtoWithProducts findByIdWithProducts(Long subcategoryId);
    SubcategoryDtoWithProducts findByTitleUrlWithProducts(String titleUrl);

    List<ProductDto> getProductsInSubcategory(Long id);
}
