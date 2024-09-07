package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.enums.OrderStatus;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.OrderRepository;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.OrderService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.OrderDto;
import ru.etu.merchstoreLETI.web.dto.OrderDtoWithProducts;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(OrderRestController.ORDER_REST_URL)
@RequiredArgsConstructor
public class OrderRestController extends CRUDRestController<OrderDto,Long> {
    public static final String ORDER_REST_URL="api/v1/order";
    public final OrderService orderService;
    public final UserService userService;
    private final OrderRepository orderRepository;
    @Override
    CRUDService<OrderDto, Long> getService() {
        return orderService;
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<OrderDto> updateEntity(@RequestBody OrderDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return ResponseEntity.ok().body(orderService.updateEntity(objectForUpdate));
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long userIdDto = objectForUpdate.getUserId();
                if (userIdDto.equals(userId)) {
                    if (userIdDto.equals(orderRepository.findById(objectForUpdate.getOrderId()).get().getUser().getId())) {
                        OrderDto objectFind = orderService.findById(objectForUpdate.getOrderId());
                        if (objectFind == null) {
                            return ResponseEntity.notFound().build();
                        }
                        orderService.updateEntity(objectForUpdate);
                        return ResponseEntity.ok(objectForUpdate);
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return null;
    }

    @Override
    @GetMapping("/findById/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            OrderDto object = orderService.findById(id);
            if (isAdmin) {
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                if (object.getUserId().equals(userId)) {
                    OrderDto objectFind = orderService.findById(id);
                    if (objectFind == null) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok(objectFind);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<OrderDto> objects = getService().findAll();
                return ResponseEntity.ok(objects);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<List<OrderDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "") String searchKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<OrderDto> objects = orderService.findAll(pageNumber,searchKey);
                return ResponseEntity.ok(objects);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                OrderDto objectForDelete = orderService.findById(id);
                orderService.deleteEntity(objectForDelete);
                return ResponseEntity.noContent().build();
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Order order = orderRepository.findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("Заказа не найдена!"));
                if (order.getUser().getId().equals(userId)) {
                    OrderDto objectForDelete = getService().findById(id);
                    if (objectForDelete == null) {
                        return ResponseEntity.notFound().build();
                    }
                    orderService.deleteEntity(objectForDelete);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PutMapping("/update/status/{orderId}/{newStatus}")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,@PathVariable OrderStatus newStatus){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                OrderDto object = orderService.updateOrderStatus(orderId,newStatus);
                return ResponseEntity.ok(object);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/findOrderByUser")
    public ResponseEntity<List<OrderDtoWithProducts>> findOrderDtoWithProductsByUser(){
        return new ResponseEntity<>(orderService.findOrderDtoWithProductsByUser(),HttpStatus.OK);
    }
    /**
     * Получает ID пользователя из объекта аутентификации.
     *
     * @param authentication Объект аутентификации пользователя.
     * @return ID пользователя.
     */
    private Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.getByEmail(userDetails.getUsername());
            if (user != null) {
                return user.getId();
            }
        }
        return null; // Или выбросить исключение, если требуется обработать ошибку
    }
}
