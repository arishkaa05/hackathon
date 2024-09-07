package ru.etu.merchstoreLETI.service.interf;

import ru.etu.merchstoreLETI.domain.enums.OrderStatus;
import ru.etu.merchstoreLETI.web.dto.OrderDto;
import ru.etu.merchstoreLETI.web.dto.OrderDtoWithProducts;

import java.util.List;

public interface OrderService extends CRUDService<OrderDto,Long>,PaginationService<OrderDto,Long> {
    OrderDto updateOrderStatus(Long orderId,OrderStatus orderStatus);

    List<OrderDtoWithProducts> findOrderDtoWithProductsByUser();
}
