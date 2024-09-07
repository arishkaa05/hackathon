package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.web.dto.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapper extends Mappable<Order, OrderDto> {
}
