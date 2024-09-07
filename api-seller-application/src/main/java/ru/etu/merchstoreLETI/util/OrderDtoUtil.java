package ru.etu.merchstoreLETI.util;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.enums.OrderStatus;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.web.dto.BucketDto;
import ru.etu.merchstoreLETI.web.dto.OrderDto;
import ru.etu.merchstoreLETI.web.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDtoUtil implements EntityDtoUtil<OrderDto, Order> {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    public  OrderDto toDto(Order entityModel) {
        OrderDto dto = new OrderDto();
        BeanUtils.copyProperties(entityModel,dto);
        dto.setOrderId(entityModel.getId());
        dto.setUserId(entityModel.getUser().getId());
        dto.setProductIds(entityModel
                .getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public  Order toEntity(OrderDto entityModelDto) {
        Order order = new Order();
        BeanUtils.copyProperties(entityModelDto,order);
        order.setStatus(OrderStatus.PROCESSING);
        order.setId(entityModelDto.getOrderId());
        User user = userRepository.findById(entityModelDto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден!"));
        if(user!=null){
            order.setUser(user);
        }
        List<Product> saveProducts = new ArrayList<>();
        for(Long productId: entityModelDto.getProductIds()){
            Product product = productRepository.findById(productId)
                    .orElseThrow(()->new ResourceNotFoundException("Продукт не найден!"));
            if(product!=null){
                saveProducts.add(product);
            }else {
                throw new ResourceNotFoundException("Продукт с данным id: "+productId+" не найден.");
            }
        }
        order.setProducts(saveProducts);
        return order;
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> entityModelList) {
        List<OrderDto> orderModelDtos = new ArrayList<>();
        for(Order model : entityModelList){
            OrderDto dto = toDto(model);
            orderModelDtos.add(dto);
        }
        return orderModelDtos;
    }

    @Override
    public List<Order> toEntityList(List<OrderDto> entityModelDtoList) {
        List<Order> orderModels = new ArrayList<>();
        for(OrderDto model : entityModelDtoList){
            Order modelAdd = toEntity(model);
            orderModels.add(modelAdd);
        }
        return orderModels;
    }
}

