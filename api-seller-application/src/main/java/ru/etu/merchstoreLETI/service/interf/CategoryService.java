package ru.etu.merchstoreLETI.service.interf;

import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;
import ru.etu.merchstoreLETI.web.dto.CategoryDtoWithProducts;
import ru.etu.merchstoreLETI.web.dto.CategoryDtoWithSubcategories;

import java.util.List;

public interface CategoryService extends CRUDService<CategoryDto,Long>,PaginationService<CategoryDto,Long> {
    CategoryDtoWithProducts findCategoryByIdWithProducts(Long id);

    List<CategoryDtoWithSubcategories> findAllWithSubcategories();

    List<CategoryDtoWithSubcategories> findByTitleWithSubcategories(String title);
}
