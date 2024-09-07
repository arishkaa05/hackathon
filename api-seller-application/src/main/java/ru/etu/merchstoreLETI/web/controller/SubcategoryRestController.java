package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.repository.OrderRepository;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.SubcategoryService;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDtoWithProducts;

import java.util.List;

@RestController
@RequestMapping(SubcategoryRestController.SUBCATEGORY_REST_CONTROLLER)
@RequiredArgsConstructor
public class SubcategoryRestController extends CRUDRestController<SubcategoryDto, Long> {
    public static final String SUBCATEGORY_REST_CONTROLLER = "api/v1/subcategory";
    private final SubcategoryService subcategoryService;
    private final OrderRepository orderRepository;

    @Override
    CRUDService<SubcategoryDto, Long> getService() {
        return subcategoryService;
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<List<SubcategoryDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "") String searchKey) {
        List<SubcategoryDto> objects = subcategoryService.findAll(pageNumber, searchKey);
        return ResponseEntity.ok(objects);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<SubcategoryDto> createEntity(@RequestBody SubcategoryDto entityForCreate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                subcategoryService.createEntity(entityForCreate);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<SubcategoryDto> updateEntity(@RequestBody SubcategoryDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return new ResponseEntity<>(subcategoryService.updateEntity(objectForUpdate), HttpStatus.OK);
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
                SubcategoryDto subcategoryDtoForDelete = subcategoryService.findById(id);
                subcategoryService.deleteEntity(subcategoryDtoForDelete);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/findById/pagination/{id}")
    public ResponseEntity<SubcategoryDtoWithProducts> findByIdWithPagination(@PathVariable Long id,
                                                                             @RequestParam(defaultValue = "0") int pageNumber,
                                                                             @RequestParam(defaultValue = "") String searchKey) {
        return new ResponseEntity<>(subcategoryService.findById(pageNumber,searchKey,id),HttpStatus.OK);
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<SubcategoryDto>> getSubcategoriesByCategory(@PathVariable Long categoryId){
        List<SubcategoryDto> subcategoryDtoList = subcategoryService.findByCategoryId(categoryId);
        return ResponseEntity.ok(subcategoryDtoList);
    }

    @GetMapping("/findById/{id}/withProducts")
    public ResponseEntity<SubcategoryDtoWithProducts> findByIdWithProducts(@PathVariable Long id) {
        return new ResponseEntity<>(subcategoryService.findByIdWithProducts(id),HttpStatus.OK);
    }

    @GetMapping("/findByTitleUrl/{titleUrl}/withProducts")
    public ResponseEntity<SubcategoryDtoWithProducts> findByTitleUrlWithProducts(@PathVariable String titleUrl) {
        return new ResponseEntity<>(subcategoryService.findByTitleUrlWithProducts(titleUrl),HttpStatus.OK);
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsInSubcategory(@PathVariable Long id){
        List<ProductDto> productDtos = subcategoryService.getProductsInSubcategory(id);
        return ResponseEntity.ok(productDtos);
    }
}
