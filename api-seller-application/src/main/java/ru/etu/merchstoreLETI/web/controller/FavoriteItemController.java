package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.FavoriteItemService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.*;

import java.util.List;

@RestController
@RequestMapping(FavoriteItemController.FAVORITE_ITEM_REST_URL)
@RequiredArgsConstructor
public class FavoriteItemController extends CRUDRestController<FavoriteItemDto,Long>{
    public static final String FAVORITE_ITEM_REST_URL="api/v1/favoriteItem";
    private final FavoriteItemService favoriteItemService;
    private final UserService userService;

    @Override
    CRUDService<FavoriteItemDto, Long> getService() {
        return favoriteItemService;
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<FavoriteItemDto> updateEntity(@RequestBody FavoriteItemDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return ResponseEntity.ok().body(favoriteItemService.updateEntity(objectForUpdate));
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long userForObject = objectForUpdate.getUserId();
                if (userForObject.equals(userId)) {
                    FavoriteItemDto objectFind = favoriteItemService.findById(objectForUpdate.getId());
                    if (objectFind == null) {
                        return ResponseEntity.notFound().build();
                    }
                    if(objectFind.getUserId().equals(userId)) {
                        favoriteItemService.updateEntity(objectForUpdate);
                        return ResponseEntity.ok(objectForUpdate);
                    }
                    return ResponseEntity.badRequest().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    @GetMapping("/findById/{id}")
    public ResponseEntity<FavoriteItemDto> findById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                FavoriteItemDto object = favoriteItemService.findById(id);
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long userIdFromBucket = favoriteItemService.findById(id).getUserId();
                if (userIdFromBucket.equals(userId)) {
                    FavoriteItemDto object = favoriteItemService.findById(id);
                    if (object == null) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok(object);
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
    public ResponseEntity<List<FavoriteItemDto>> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<FavoriteItemDto> objects = getService().findAll();
                return ResponseEntity.ok(objects);
            } else {
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
                FavoriteItemDto objectForDelete = getService().findById(id);
                getService().deleteEntity(objectForDelete);
                return ResponseEntity.noContent().build();
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                FavoriteItemDto objectForDelete = getService().findById(id);
                if (objectForDelete.getUserId().equals(userId)) {
                    getService().deleteEntity(objectForDelete);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<FavoriteItemDto> getFavoriteItemDtoByUserId(@PathVariable Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                FavoriteItemDto object = favoriteItemService.findFavoriteItemDtoByUserId(userId);
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userIdFromAuthentication = getUserIdFromAuthentication(authentication);
                if (userId.equals(userIdFromAuthentication)) {
                    FavoriteItemDto object = favoriteItemService.findFavoriteItemDtoByUserId(userId);
                    if (object == null) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok(object);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/updateProducts")
    public ResponseEntity<FavoriteItemDto> updateFavoriteItemProducts(
            @RequestBody FavoriteItemDtoOnlyProducts favoriteItemDtoOnlyProducts
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(favoriteItemService.updateFavoriteItemsProducts(authentication,favoriteItemDtoOnlyProducts),HttpStatus.OK);
    }

    @PostMapping("/createFavoriteItem")
    public ResponseEntity<MessageResponse> createFavoriteItem(){
        favoriteItemService.createFavoriteItemWithoutProducts();
        return ResponseEntity.ok(new MessageResponse("Избранное успешно создано у вашего пользователя!"));
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
