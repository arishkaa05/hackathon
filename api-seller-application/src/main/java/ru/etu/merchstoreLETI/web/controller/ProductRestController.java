package ru.etu.merchstoreLETI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.ProductDtoWithCategory;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(ProductRestController.PRODUCT_REST_URL)
@RequiredArgsConstructor
public class ProductRestController extends CRUDRestController<ProductDto, Long> {
    public static final String PRODUCT_REST_URL = "api/v1/product";
    private final ProductService productService;

    @Override
    CRUDService<ProductDto, Long> getService() {
        return productService;
    }

    @GetMapping("/all/pagination")
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "") String searchKey) {
        List<ProductDto> objects = productService.findAll(pageNumber,searchKey);
        return ResponseEntity.ok(objects);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createEntity(@RequestBody ProductDto entityForCreate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                productService.createEntity(entityForCreate);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateEntity(@RequestBody ProductDto objectForUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                productService.updateEntity(objectForUpdate);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<Void> deleteEntity(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверьте роль пользователя
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                ProductDto productDtoForDelete = productService.findById(id);
                productService.deleteEntity(productDtoForDelete);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{productId}/subcategories")
    public ResponseEntity<List<SubcategoryDto>> getProductSubcategories(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.getProductSubcategories(productId),HttpStatus.OK);
    }

    @GetMapping("/{productId}/categories")
    public ResponseEntity<ProductDtoWithCategory> getProductCategories(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.getProductCategories(productId),HttpStatus.OK);
    }

    @GetMapping("/new-products")
    public ResponseEntity<List<ProductDto>> getNewProducts(){
        return new ResponseEntity<>(productService.getNewProducts(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProduct(
            @RequestParam(required = false) BigDecimal minOldPrice,
            @RequestParam(required = false) BigDecimal maxOldPrice,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        List<ProductDto> matchingProducts = productService.searchProducts(minOldPrice, maxOldPrice, minPrice, maxPrice);
        return ResponseEntity.ok(matchingProducts);
    }

    @GetMapping("/findByTitleForUrl/{titleUrl}")
    public ResponseEntity<ProductDto> findByTitleUrl(@PathVariable String titleUrl){
        ProductDto object = productService.findByTitleUrl(titleUrl);
        if(object==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(object);
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
