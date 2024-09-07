package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.CategoryService;
import ru.etu.merchstoreLETI.web.dto.*;

import java.util.List;

@RestController
@RequestMapping(CategoryRestController.CATEGORY_REST_URL)
@RequiredArgsConstructor
public class CategoryRestController extends CRUDRestController<CategoryDto, Long> {
    public static final String CATEGORY_REST_URL = "api/v1/category";
    private final CategoryService categoryService;

    @Override
    CRUDService<CategoryDto, Long> getService() {
        return categoryService;
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateEntity(@RequestBody CategoryDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return new ResponseEntity<>(categoryService.updateEntity(objectForUpdate), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createEntity(@RequestBody CategoryDto entityForCreate) {
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

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                CategoryDto categoryDtoForDelete = categoryService.findById(id);
                categoryService.deleteEntity(categoryDtoForDelete);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<List<CategoryDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "") String searchKey){
        List<CategoryDto> objects = categoryService.findAll(pageNumber,searchKey);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/findById/{id}/products")
    public ResponseEntity<CategoryDtoWithProducts> findByIdWithProducts(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findCategoryByIdWithProducts(id),HttpStatus.OK);
    }

    @GetMapping("/all/withSubcategories")
    public ResponseEntity<List<CategoryDtoWithSubcategories>> findAllWithSubcategories() {
        List<CategoryDtoWithSubcategories> objects = categoryService.findAllWithSubcategories();
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/findByTitle/{title}/withSubcategories")
    public ResponseEntity<List<CategoryDtoWithSubcategories>> findByTitleWithSubcategories(@PathVariable String title) {
        List<CategoryDtoWithSubcategories> objects = categoryService.findByTitleWithSubcategories(title);
        return ResponseEntity.ok(objects);
    }
}

