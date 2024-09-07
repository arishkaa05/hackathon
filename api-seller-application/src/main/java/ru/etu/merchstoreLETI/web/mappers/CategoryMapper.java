package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.web.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends Mappable<Category, CategoryDto> {
}
