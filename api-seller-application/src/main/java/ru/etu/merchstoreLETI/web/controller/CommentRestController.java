package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.CommentService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.web.dto.CommentDto;
import ru.etu.merchstoreLETI.web.dto.CommentDtoWithUsername;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(CommentRestController.COMMENT_REST_URL)
@RequiredArgsConstructor
public class CommentRestController extends CRUDRestController<CommentDto,Long> {
    public static final String COMMENT_REST_URL="api/v1/comment";
    private final CommentService commentService;
    private final UserService userService;
    @Override
    CRUDService<CommentDto, Long> getService() {
        return commentService;
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<CommentDto> updateEntity(@RequestBody CommentDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return ResponseEntity.ok().body(commentService.updateEntity(objectForUpdate));
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                Long objectId = objectForUpdate.getUserId();
                if (objectId.equals(userId)) {
                    CommentDto objectFind = commentService.findById(objectForUpdate.getId());
                    if (objectFind == null) {
                        return ResponseEntity.notFound().build();
                    }
                    if(objectFind.getUserId().equals(objectId)) {
                        commentService.updateEntity(objectForUpdate);
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                CommentDto objectForDelete = getService().findById(id);
                getService().deleteEntity(objectForDelete);
                return ResponseEntity.noContent().build();
            } else {
                Long userId = getUserIdFromAuthentication(authentication);
                CommentDto objectForDelete = getService().findById(id);
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


    @GetMapping("/all/pagination")
    public ResponseEntity<List<CommentDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "") String searchKey){
        List<CommentDto> objects = commentService.findAll(pageNumber,searchKey);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDto>> getCommentsByProduct(@PathVariable Long productId) {
        List<CommentDto> comments = commentService.getCommentsByProduct(productId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/product/{productId}/withUserUsername")
    public ResponseEntity<List<CommentDtoWithUsername>> getCommentsByProductWithUsername(@PathVariable Long productId) {
        List<CommentDtoWithUsername> comments = commentService.getCommentsByProductWithUser(productId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                List<CommentDto> comments = commentService.getCommentsByUser(userId);
                return ResponseEntity.ok(comments);
            } else {
                Long userIdFromAuthentication = getUserIdFromAuthentication(authentication);
                if (userId.equals(userIdFromAuthentication)) {
                    List<CommentDto> comments = commentService.getCommentsByUser(userId);
                    return ResponseEntity.ok(comments);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PatchMapping("/{id}/update-rating")
    public ResponseEntity<CommentDto> updateCommentRating(@PathVariable Long id,
                                                         @RequestParam Integer newRating){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean isAdmin = authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                CommentDto updatedComment = commentService.updateCommentRating(id, newRating);
                return ResponseEntity.ok(updatedComment);
            } else {
                Long userIdFromAuthentication = getUserIdFromAuthentication(authentication);
                if (commentService.findById(id).getUserId().equals(userIdFromAuthentication)) {
                    CommentDto updatedComment = commentService.updateCommentRating(id, newRating);
                    return ResponseEntity.ok(updatedComment);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> searchComments(
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        List<CommentDto> matchingComments = commentService.searchComments(minRating, maxRating, startDate, endDate);
        return ResponseEntity.ok(matchingComments);
    }

}
