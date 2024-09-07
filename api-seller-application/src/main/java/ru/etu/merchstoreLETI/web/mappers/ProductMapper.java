package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.web.dto.ProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Mappable<Product, ProductDto> {
}
