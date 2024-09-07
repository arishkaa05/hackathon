package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.web.dto.BucketDto;
import ru.etu.merchstoreLETI.web.dto.OrderDtoWithProducts;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDtoWithProductsUtil {
    private final ProductDtoUtil productDtoUtil;
    public OrderDtoWithProducts toDto(Order entityModel) {
        OrderDtoWithProducts dto = new OrderDtoWithProducts();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setOrderId(entityModel.getId());
        dto.setProducts(productDtoUtil.toDtoList(entityModel.getProducts()));
        return dto;
    }

    public List<OrderDtoWithProducts> toDtoList(List<Order> entityModelList) {
        List<OrderDtoWithProducts> orderModelDtos = new ArrayList<>();
        for(Order model : entityModelList){
            OrderDtoWithProducts dto = toDto(model);
            orderModelDtos.add(dto);
        }
        return orderModelDtos;
    }
}
