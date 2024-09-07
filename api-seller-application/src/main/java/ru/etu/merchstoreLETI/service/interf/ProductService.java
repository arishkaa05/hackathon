package ru.etu.merchstoreLETI.service.interf;

import jakarta.validation.constraints.Size;
import ru.etu.merchstoreLETI.domain.enums.SizeEnum;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.web.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService extends CRUDService<ProductDto,Long>,PaginationService<ProductDto,Long> {
    List<SubcategoryDto> getProductSubcategories(Long productId);

    ProductDtoWithCategory getProductCategories(Long productId);

    List<ProductDto> getNewProducts();

    List<ProductDto> searchProducts(BigDecimal minOldPrice, BigDecimal maxOldPrice, BigDecimal minPrice, BigDecimal maxPrice);

    ProductDto findByTitleUrl(String title);

    List<ProductDto> getProductsByCategory(Long categoryId);

}
