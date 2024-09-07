package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.enums.SizeEnum;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.*;
import ru.etu.merchstoreLETI.repository.*;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.util.*;
import ru.etu.merchstoreLETI.web.dto.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation extends AbstractCRUDService<ProductDto, Long, Product> implements ProductService, PaginationService<ProductDto,Long> {
    private final ProductDtoUtil productDtoUtil;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final BucketRepository bucketRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryDtoUtil subcategoryDtoUtil;
    private final CategoryDtoUtil categoryDtoUtil;
    private final ProductDtoWithCategoriesUtil productDtoWithCategoriesUtil;
    private final CategoryRepository categoryRepository;
    @Override
    JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    EntityDtoUtil<ProductDto, Product> getEntityDtoUtil() {
        return productDtoUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if(searchKey.equals("")){
            List<Product> objects = new ArrayList<>();
            getRepository().findAll(pageable).forEach(objects::add);
            List<ProductDto> entityDtoList = getEntityDtoUtil().toDtoList(objects);
            return entityDtoList;
        }else {
            List<Product> objects = new ArrayList<>();
            productRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    searchKey,searchKey,pageable
            ).forEach(objects::add);
            List<ProductDto> entityDtoList = getEntityDtoUtil().toDtoList(objects);
            return entityDtoList;
        }

    }

    @Override
    @Transactional
    public void deleteEntity(ProductDto objectDto) {
        Product product = productRepository.findById(objectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден!"));
        List<Bucket> bucketsWithProduct = bucketRepository.findByProductsContaining(product);
        for (Bucket bucket : bucketsWithProduct) {
            bucket.getProducts().remove(product);
            bucket.setTotalCost(calculateTotalCost(bucket));
            bucket.setAmountProducts(bucket.getProducts().size());
            bucketRepository.save(bucket);
        }
        List<Subcategory> subcategoryWithProduct = subcategoryRepository.findByProductsContaining(product);
        for(Subcategory subcategory: subcategoryWithProduct){
            subcategory.getProducts().remove(product);
            subcategoryRepository.save(subcategory);
        }
        commentRepository.deleteAll(product.getComments());
        productRepository.delete(product);
    }

    private BigDecimal calculateTotalCost(Bucket bucket) {
        List<Product> products = bucket.getProducts();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Product product : products) {
            if (product.getHotProduct()) {
                BigDecimal productPrice = product.getPrice();
                totalCost = totalCost.add(productPrice);
            } else {
                BigDecimal productPrice = product.getOldPrice();
                totalCost = totalCost.add(productPrice);
            }
        }
        return totalCost;
    }

    @Override
    public List<SubcategoryDto> getProductSubcategories(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Продукт с id: "+productId+" не найден!"));
        Set<Subcategory> subcategories = product.getSubcategories();
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        for(Subcategory subcategory: subcategories){
            SubcategoryDto subcategoryDto = subcategoryDtoUtil.toDto(subcategory);
            List<Long> productIds = subcategory
                    .getProducts()
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            subcategoryDto.setProductIds(productIds);
            subcategoryDtoList.add(subcategoryDto);
        }
        return subcategoryDtoList;
    }

    @Override
    public ProductDtoWithCategory getProductCategories(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Продукт с id: "+productId+" не найден!"));
        Set<Subcategory> subcategories = product.getSubcategories();
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Subcategory subcategory: subcategories){
            for (Category category: subcategory.getCategories()){
                CategoryDto categoryDto = categoryDtoUtil.toDto(category);
                List<Long> subcategoryIds = category
                        .getSubcategories()
                        .stream()
                        .map(Subcategory::getId)
                        .collect(Collectors.toList());
                categoryDto.setSubcategoryIds(subcategoryIds);
                categoryDtoList.add(categoryDto);
            }
            SubcategoryDto subcategoryDto = subcategoryDtoUtil.toDto(subcategory);
            List<Long> productIds = subcategory
                    .getProducts()
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            subcategoryDto.setProductIds(productIds);
            subcategoryDtoList.add(subcategoryDto);
        }
        ProductDtoWithCategory productDtoWithCategory = productDtoWithCategoriesUtil.toDto(product);
        productDtoWithCategory.setCategoryDtoList(categoryDtoList);
        return productDtoWithCategory;
    }

    @Override
    public List<ProductDto> getNewProducts() {
        List<Product> newProduct = productRepository.findByNewProduct(true,PageRequest.of(0,10));
        return productDtoUtil.toDtoList(newProduct);
    }

    @Override
    public List<ProductDto> searchProducts(BigDecimal minOldPrice, BigDecimal maxOldPrice, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> matchingProducts = productRepository.searchProducts(minOldPrice, maxOldPrice, minPrice, maxPrice);
        return productDtoUtil.toDtoList(matchingProducts);
    }

    @Override
    public ProductDto findByTitleUrl(String title) {
        Product entityFind = productRepository.findByTitleForUrl(title)
                .orElseThrow(()->new ResourceNotFoundException("Данная продукт не найден по titleUrl!"));
        return productDtoUtil.toDto(entityFind);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Категория с данным id: "+categoryId+" не найдена!"));
        List<Product> products = new ArrayList<>();
        for(Subcategory subcategory: category.getSubcategories()){
            for(Product product: subcategory.getProducts()){
                products.add(product);
            }
        }
        return productDtoUtil.toDtoList(products);
    }
}