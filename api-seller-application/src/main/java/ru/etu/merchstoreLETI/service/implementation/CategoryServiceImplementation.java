package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.enums.Role;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.CategoryRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.SubcategoryRepository;
import ru.etu.merchstoreLETI.service.interf.CategoryService;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.SubcategoryService;
import ru.etu.merchstoreLETI.util.*;
import ru.etu.merchstoreLETI.web.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation extends AbstractCRUDService<CategoryDto, Long, Category> implements CategoryService, PaginationService<CategoryDto,Long> {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoUtil categoryDtoUtil;
    private final SubcategoryService subcategoryService;
    private final SubcategoryDtoUtil subcategoryDtoUtil;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductRepository productRepository;
    private final ProductDtoUtil productDtoUtil;
    private final CategoryDtoWithSubcategoriesUtil categoryDtoWithSubcategoriesUtil;

    @Override
    JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    EntityDtoUtil<CategoryDto, Category> getEntityDtoUtil() {
        return categoryDtoUtil;
    }

    @Override
    public CategoryDto updateEntity(CategoryDto objectDto) {
        Category existingCategory = categoryRepository.findById(objectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена!"));
        existingCategory.setDescription(objectDto.getDescription());
        existingCategory.setTitle(objectDto.getTitle());
        existingCategory.setTitleUrl(objectDto.getTitleUrl());
        existingCategory.setImageUrl(objectDto.getImageUrl());
        List<Subcategory> updatedSubcategories = new ArrayList<>();
        for (Long subcategoryId : objectDto.getSubcategoryIds()) {
            SubcategoryDto subcategoryDto = subcategoryService.findById(subcategoryId);
            if (subcategoryDto != null) {
                Subcategory savedSubcategory = subcategoryDtoUtil.toEntity(subcategoryDto);
                updatedSubcategories.add(savedSubcategory);
            } else {
                throw new ResourceNotFoundException("Подкатегория с ID: " + subcategoryId + " не найдена!");
            }
        }
        existingCategory.setSubcategories(updatedSubcategories);
        Category updatedCategory = categoryRepository.save(existingCategory);
        CategoryDto categoryDto = categoryDtoUtil.toDto(updatedCategory);
        List<Long> subcategoryIdsFind = new ArrayList<>();
        for (Subcategory subcategory : updatedCategory.getSubcategories()) {
            subcategoryIdsFind.add(subcategory.getId());
        }
        categoryDto.setSubcategoryIds(subcategoryIdsFind);
        return categoryDto;
    }

    @Override
    @Transactional
    public void createEntity(CategoryDto objectDto) {
        if (categoryRepository.findCategoryByTitle(objectDto.getTitle()).isPresent()) {
            throw new IllegalStateException("Категория с данным названием уже существует");
        }
        Category category = getEntityDtoUtil().toEntity(objectDto);
        List<Subcategory> saveSubcategories = new ArrayList<>();
        for (Long subcategoryId : objectDto.getSubcategoryIds()) {
            Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Подкатегория с данным id: " + subcategoryId + " не найдена"));
            saveSubcategories.add(subcategory);
        }
        category.setSubcategories(saveSubcategories);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Category entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная категория не найдена!"));
        CategoryDto categoryDto =getEntityDtoUtil().toDto(entityFind);
        List<Long> subcategoryIdsFind = new ArrayList<>();
        for(Subcategory subcategory: entityFind.getSubcategories()){
            subcategoryIdsFind.add(subcategory.getId());
        }
        categoryDto.setSubcategoryIds(subcategoryIdsFind);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = getRepository().findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category category: categories){
            CategoryDto categoryDto = getEntityDtoUtil().toDto(category);
            List<Long> subcategoryIds = category
                    .getSubcategories()
                    .stream()
                    .map(Subcategory::getId)
                    .collect(Collectors.toList());
            categoryDto.setSubcategoryIds(subcategoryIds);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Override
    public List<CategoryDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if (searchKey.equals("")) {
            Page<Category> categories = getRepository().findAll(pageable);
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category : categories) {
                CategoryDto categoryDto = getEntityDtoUtil().toDto(category);
                List<Long> subcategoryIds = category
                        .getSubcategories()
                        .stream()
                        .map(Subcategory::getId)
                        .collect(Collectors.toList());
                categoryDto.setSubcategoryIds(subcategoryIds);
                categoryDtos.add(categoryDto);
            }
            return categoryDtos;
        }else {
            List<Category> categories = categoryRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    searchKey,searchKey,pageable
            );
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category : categories) {
                CategoryDto categoryDto = getEntityDtoUtil().toDto(category);
                List<Long> subcategoryIds = category
                        .getSubcategories()
                        .stream()
                        .map(Subcategory::getId)
                        .collect(Collectors.toList());
                categoryDto.setSubcategoryIds(subcategoryIds);
                categoryDtos.add(categoryDto);
            }
            return categoryDtos;
        }
    }

    @Override
    public CategoryDtoWithProducts findCategoryByIdWithProducts(Long id) {
        Category entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная категория не найдена!"));
        CategoryDtoWithProducts categoryDtoWithProducts =new CategoryDtoWithProducts();
        categoryDtoWithProducts.setId(entityFind.getId());
        categoryDtoWithProducts.setTitle(entityFind.getTitle());
        categoryDtoWithProducts.setTitleUrl(entityFind.getTitleUrl());
        categoryDtoWithProducts.setDescription(entityFind.getDescription());
        categoryDtoWithProducts.setImageUrl(entityFind.getImageUrl());
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Subcategory subcategory: entityFind.getSubcategories()){
            SubcategoryDto subcategoryDto =subcategoryDtoUtil.toDto(subcategory);
            List<Long> productIdsFind = new ArrayList<>();
            for(Product product: subcategory.getProducts()){
                productIdsFind.add(product.getId());
                ProductDto productDto =productDtoUtil.toDto(product);
                productDtos.add(productDto);
            }
            subcategoryDto.setProductIds(productIdsFind);
            subcategoryDtoList.add(subcategoryDto);
        }
        categoryDtoWithProducts.setSubcategoryDtos(subcategoryDtoList);
        categoryDtoWithProducts.setProductDtos(productDtos);
        return categoryDtoWithProducts;
    }

    @Override
    public List<CategoryDtoWithSubcategories> findAllWithSubcategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDtoWithSubcategories> categoryDtoWithSubcategories = new ArrayList<>();
        for (Category category : categories) {
            CategoryDtoWithSubcategories categoryDtoWithSubcategory = categoryDtoWithSubcategoriesUtil.toDto(category);
            categoryDtoWithSubcategories.add(categoryDtoWithSubcategory);
        }
        return categoryDtoWithSubcategories;
    }

    @Override
    public List<CategoryDtoWithSubcategories> findByTitleWithSubcategories(String title) {
        List<Category> categories = categoryRepository.findByTitleUrl(title);
        List<CategoryDtoWithSubcategories> categoryDtoWithSubcategories = new ArrayList<>();
        for (Category category : categories) {
            CategoryDtoWithSubcategories categoryDtoWithSubcategory = categoryDtoWithSubcategoriesUtil.toDto(category);
            categoryDtoWithSubcategories.add(categoryDtoWithSubcategory);
        }
        return categoryDtoWithSubcategories;
    }
}
