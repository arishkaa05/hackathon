package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.etu.merchstoreLETI.domain.enums.OrderStatus;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.OrderRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.service.interf.OrderService;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.util.*;
import ru.etu.merchstoreLETI.web.dto.*;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation extends AbstractCRUDService<OrderDto,Long,Order> implements OrderService, PaginationService<OrderDto,Long> {
    private final OrderRepository orderRepository;
    private final OrderDtoUtil orderDtoUtil;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDtoWithProductsUtil orderDtoWithProductsUtil;
    @Override
    JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    EntityDtoUtil<OrderDto, Order> getEntityDtoUtil() {
        return orderDtoUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данный заказ не найден!"));
        OrderDto orderDto = orderDtoUtil.toDto(entityFind);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto updateEntity(OrderDto objectDto) {
        Order existingOrder = orderRepository.findById(objectDto.getOrderId())
                .orElseThrow(()->new ResourceNotFoundException("Заказ не найден!"));
        existingOrder.setAddress(objectDto.getAddress());
        existingOrder.setStatus(objectDto.getStatus());
        existingOrder.setDeliveryPrice(objectDto.getDeliveryPrice());
        User user = userRepository.findById(objectDto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден!"));
        if(user!=null){
            existingOrder.setUser(user);
        }
        List<Product> updatedProducts = new ArrayList<>();
        for(Long productId: objectDto.getProductIds()){
            Product product = productRepository.findById(productId)
                    .orElseThrow(()->new ResourceNotFoundException("Продукт не найден!"));
            if(product!=null) {
                updatedProducts.add(product);
            }else {
                throw new ResourceNotFoundException("Продукт с ID: "+productId+" не найден!");
            }
        }
        existingOrder.setProducts(updatedProducts);
        Order updatedOrder = orderRepository.save(existingOrder);
        OrderDto orderDto =orderDtoUtil.toDto(updatedOrder);
        return orderDto;
    }

    @Override
    @Transactional
    public void createEntity(OrderDto objectDto) {
        Order newOrder = orderDtoUtil.toEntity(objectDto);
        orderRepository.save(newOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        List<Order> orders = getRepository().findAll();
        List<OrderDto> orderDtos = orderDtoUtil.toDtoList(orders);
        return orderDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if(searchKey.equals("")) {
            Page<Order> orders = getRepository().findAll(pageable);
            List<OrderDto> orderDtos = new ArrayList<>();
            for (Order order : orders) {
                OrderDto orderDto = getEntityDtoUtil().toDto(order);
                orderDtos.add(orderDto);
            }
            return orderDtos;
        }else {
            List<Order> orders = orderRepository.findByAddressContainingIgnoreCaseOrStatusContainingIgnoreCase(
                    searchKey,searchKey,pageable
            );
            List<OrderDto> orderDtos = orderDtoUtil.toDtoList(orders);
            return orderDtos;
        }
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId,OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Заказ не найден!"));
        order.setStatus(orderStatus);
        orderRepository.save(order);
        OrderDto orderDto = orderDtoUtil.toDto(order);
        return orderDto;
    }

    @Override
    public List<OrderDtoWithProducts> findOrderDtoWithProductsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден!"));
        List<OrderDtoWithProducts> orderDtoWithProducts = orderDtoWithProductsUtil.toDtoList(user.getOrders());
        return orderDtoWithProducts;
    }
}
