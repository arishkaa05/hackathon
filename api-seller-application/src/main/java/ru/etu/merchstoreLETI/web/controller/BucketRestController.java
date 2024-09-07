package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.service.interf.BucketService;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.*;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.util.List;

@RestController
@RequestMapping(BucketRestController.BUCKET_REST_URL)
@RequiredArgsConstructor
public class BucketRestController extends CRUDRestController<BucketDto,Long> {
    public static final String BUCKET_REST_URL = "api/v1/bucket";

    private final BucketService bucketService;
    private final UserService userService;
    @Override
    CRUDService<BucketDto, Long> getService() {
        return bucketService;
    }

    @Override
    public ResponseEntity<BucketDto> updateEntity(BucketDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return ResponseEntity.ok().body(bucketService.updateEntity(objectForUpdate));
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long userForObject = objectForUpdate.getUserId();
                if (userForObject.equals(userId)) {
                    BucketDto objectFind = bucketService.findById(objectForUpdate.getId());
                    if (objectFind == null) {
                        return ResponseEntity.notFound().build();
                    }
                    if(objectFind.getUserId().equals(userId)) {
                        bucketService.updateEntity(objectForUpdate);
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
    public ResponseEntity<BucketDto> findById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                BucketDto object = bucketService.findById(id);
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long userIdFromBucket = bucketService.findById(id).getUserId();
                if (userIdFromBucket.equals(userId)) {
                    BucketDto object = bucketService.findById(id);
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
    public ResponseEntity<List<BucketDto>> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<BucketDto> objects = getService().findAll();
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
                BucketDto objectForDelete = getService().findById(id);
                getService().deleteEntity(objectForDelete);
                return ResponseEntity.noContent().build();
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                BucketDto objectForDelete = getService().findById(id);
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

    @GetMapping("/all/pagination")
    public ResponseEntity<List<BucketDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "") String searchKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<BucketDto> objects = bucketService.findAll(pageNumber,searchKey);
                return ResponseEntity.ok(objects);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<BucketDto> createEntity(BucketDto entityForCreate) {
        bucketService.createEntity(entityForCreate);
        return ResponseEntity.ok(entityForCreate);
    }

    @PostMapping("/createBucket")
    public ResponseEntity<MessageResponse> createBucket(){
        bucketService.createBucketWithoutProducts();
        return ResponseEntity.ok(new MessageResponse("Корзина успешно создана у вашего пользователя!"));
    }

    @PatchMapping("/updateProducts")
    public ResponseEntity<BucketDtoWithProducts> updateBucketProducts(
            @RequestBody BucketDtoOnlyProducts updatedBucketDto
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(bucketService.updateBucketProducts(authentication,updatedBucketDto),HttpStatus.OK);
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<BucketDtoWithProducts> findBucketByUserId(@PathVariable Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                BucketDtoWithProducts object = bucketService.findBucketByUserId(userId);
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userIdFromAuthentication = getUserIdFromAuthentication(authentication);
                if (userId.equals(userIdFromAuthentication)) {
                    BucketDtoWithProducts object = bucketService.findBucketByUserId(userId);
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
