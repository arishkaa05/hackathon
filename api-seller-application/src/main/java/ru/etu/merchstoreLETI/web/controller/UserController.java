package ru.etu.merchstoreLETI.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.MessageResponse;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(UserController.USER_REST_URL)

public class UserController extends CRUDRestController<UserDto, Long> {
    public static final String USER_REST_URL = "api/v1/user";

    @Autowired
    private UserService userService;

    @Override
    CRUDService<UserDto, Long> getService() {
        return userService;
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<List<UserDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "") String searchKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<UserDto> objects = userService.findAll(pageNumber,searchKey);
                return ResponseEntity.ok(objects);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Обрабатывает PUT-запрос для обновления информации о пользователе.
     *
     * @param objectForUpdate Пользователь с обновленными данными.
     * @return ResponseEntity с обновленным пользователем или ошибкой.
     */
    @Override
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateEntity(@RequestBody UserDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return ResponseEntity.ok().body(userService.updateEntity(objectForUpdate));
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long objectId = objectForUpdate.getId();
                if (objectId.equals(userId)) {
                    UserDto objectFind = userService.findById(objectId);
                    if (objectFind == null) {
                        return ResponseEntity.notFound().build();
                    }
                    userService.updateEntity(objectForUpdate);
                    return ResponseEntity.ok(objectForUpdate);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    /**
     * Обрабатывает POST-запрос для создания нового пользователя.
     *
     * @param entityForCreate Пользователь для создания.
     * @return ResponseEntity с созданным пользователем или ошибкой.
     */
    @Override
    @PostMapping("/create")
    public ResponseEntity<UserDto> createEntity(@RequestBody UserDto entityForCreate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                getService().createEntity(entityForCreate);
                return ResponseEntity.ok(entityForCreate);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    /**
     * Обрабатывает GET-запрос для поиска пользователя по ID.
     *
     * @param id ID пользователя для поиска.
     * @return ResponseEntity с найденным пользователем или ошибкой.
     */
    @Override
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                UserDto object = userService.findById(id);
                if (object == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(object);
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                if (id.equals(userId)) {
                    UserDto object = userService.findById(id);
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
     * Обрабатывает GET-запрос для поиска всех пользователей.
     *
     * @return ResponseEntity со списком пользователей или ошибкой.
     */
    @Override
    public ResponseEntity<List<UserDto>> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return super.findAll();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Обрабатывает DELETE-запрос для удаления пользователя по ID.
     *
     * @param id ID пользователя для удаления.
     * @return ResponseEntity с успешным статусом или ошибкой.
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                UserDto objectForDelete = getService().findById(id);
                getService().deleteEntity(objectForDelete);
                return ResponseEntity.noContent().build();
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                if (id.equals(userId)) {
                    UserDto objectForDelete = getService().findById(id);
                    if (objectForDelete == null) {
                        return ResponseEntity.notFound().build();
                    }
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

    @GetMapping("/search/createdDate")
    public ResponseEntity<List<UserDto>> searchUserByCreateDate(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime minCreateDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime maxCreateDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<UserDto> matchingUsers = userService.searchUsers(minCreateDate, maxCreateDate);
                return ResponseEntity.ok(matchingUsers);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            return ResponseEntity.ok(userService.findUserRoles(authentication));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            UserDto userDto = userService.getUserDtoByEmail(userDetails.getUsername());
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Пользователь не зарегестрирован или не вошел в аккаунт!"),HttpStatus.UNAUTHORIZED);
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

