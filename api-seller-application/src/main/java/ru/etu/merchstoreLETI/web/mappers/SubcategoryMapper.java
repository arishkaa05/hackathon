package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper extends Mappable<Subcategory, SubcategoryDto> {

}
