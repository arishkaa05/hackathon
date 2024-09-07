package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.CategoryRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.SubcategoryRepository;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.PaginationServiceFindById;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.service.interf.SubcategoryService;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;
import ru.etu.merchstoreLETI.util.ProductDtoUtil;
import ru.etu.merchstoreLETI.util.SubcategoryDtoUtil;
import ru.etu.merchstoreLETI.util.SubcategoryDtoWithProductsUtil;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDtoWithProducts;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImplementation extends AbstractCRUDService<SubcategoryDto,Long,Subcategory> implements SubcategoryService, PaginationService<SubcategoryDto,Long>, PaginationServiceFindById<SubcategoryDtoWithProducts,Long> {
    private final SubcategoryDtoUtil subcategoryDtoUtil;
    private final ProductService productService;
   private final ProductDtoUtil productDtoUtil;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryDtoWithProductsUtil subcategoryDtoWithProductsUtil;
    @Override
    JpaRepository<Subcategory, Long> getRepository() {
        return subcategoryRepository;
    }

    @Override
    EntityDtoUtil<SubcategoryDto, Subcategory> getEntityDtoUtil() {
        return subcategoryDtoUtil;
    }

    @Override
    @Transactional
    public SubcategoryDto updateEntity(SubcategoryDto objectDto) {
       Subcategory existingSubcategory = subcategoryRepository.findById(objectDto.getId())
               .orElseThrow(()->new ResourceNotFoundException("Подкатегория не найдена!"));
       existingSubcategory.setDescription(objectDto.getDescription());
       existingSubcategory.setTitle(objectDto.getTitle());
       existingSubcategory.setTitleUrl(objectDto.getTitleUrl());
       existingSubcategory.setQuantity(objectDto.getQuantity());
       existingSubcategory.setImageUrl(objectDto.getImageUrl());
        List<Product> updatedProducts = new ArrayList<>();
        for(Long productId: objectDto.getProductIds()){
            ProductDto productDto = productService.findById(productId);
            if(productDto!=null) {
                Product savedProduct = productDtoUtil.toEntity(productDto);
                updatedProducts.add(savedProduct);
            }else {
                throw new ResourceNotFoundException("Продукт с ID: "+productId+" не найден!");
            }
        }
        existingSubcategory.setProducts(updatedProducts);
        Subcategory updatedSubcategory = subcategoryRepository.save(existingSubcategory);
        SubcategoryDto subcategoryDto =subcategoryDtoUtil.toDto(updatedSubcategory);
        List<Long> productIdsFind = new ArrayList<>();
        for(Product product: updatedSubcategory.getProducts()){
            productIdsFind.add(product.getId());
        }
        subcategoryDto.setProductIds(productIdsFind);
        return subcategoryDto;
    }

    @Override
    @Transactional
    public void createEntity(SubcategoryDto objectDto) {
        Subcategory newSubcategory = new Subcategory();
        newSubcategory.setDescription(objectDto.getDescription());
        newSubcategory.setTitle(objectDto.getTitle());
        newSubcategory.setTitleUrl(objectDto.getTitleUrl());
        newSubcategory.setQuantity(objectDto.getQuantity());
        newSubcategory.setImageUrl(objectDto.getImageUrl());
        List<Product> saveProducts = new ArrayList<>();
        for(Long productId: objectDto.getProductIds()){
            ProductDto productDto = productService.findById(productId);
            if(productDto!=null){
                Product saveProduct = productDtoUtil.toEntity(productDto);
                saveProducts.add(saveProduct);
            }else {
                throw new ResourceNotFoundException("Продукт с данным id: "+productId+" не найден.");
            }
        }
        newSubcategory.setProducts(saveProducts);
        subcategoryRepository.save(newSubcategory);
    }

    @Override
    @Transactional(readOnly = true)
    public SubcategoryDto findById(Long id) {
        Subcategory entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная подкатегория не найдена!"));
        SubcategoryDto subcategoryDto =getEntityDtoUtil().toDto(entityFind);
        List<Long> productIdsFind = new ArrayList<>();
        for(Product product: entityFind.getProducts()){
            productIdsFind.add(product.getId());
        }
        subcategoryDto.setProductIds(productIdsFind);
        return subcategoryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubcategoryDto> findAll() {
        List<Subcategory> subcategories = getRepository().findAll();
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        for(Subcategory subcategory: subcategories){
            SubcategoryDto subcategoryDto = getEntityDtoUtil().toDto(subcategory);
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
    @Transactional(readOnly = true)
    public List<SubcategoryDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if(searchKey.equals("")) {
            Page<Subcategory> subcategories = getRepository().findAll(pageable);
            List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
            for (Subcategory subcategory : subcategories) {
                SubcategoryDto subcategoryDto = getEntityDtoUtil().toDto(subcategory);
                List<Long> productIds = subcategory
                        .getProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
                subcategoryDto.setProductIds(productIds);
                subcategoryDtoList.add(subcategoryDto);
            }
            return subcategoryDtoList;
        }else {
            List<Subcategory> subcategories = subcategoryRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    searchKey,searchKey,pageable
            );
            List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
            for (Subcategory subcategory : subcategories) {
                SubcategoryDto subcategoryDto = getEntityDtoUtil().toDto(subcategory);
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
    }

    @Override
    @Transactional(readOnly = true)
    public SubcategoryDtoWithProducts findById(int pageNumber, String searchKey,Long id) {
        Subcategory entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная подкатегория не найдена!"));
        Pageable pageable = PageRequest.of(pageNumber,10);
        SubcategoryDtoWithProducts subcategoryDtoWithProducts = new SubcategoryDtoWithProducts();
        if(searchKey.equals("")) {
            List<Product> objects = new ArrayList<>();
            productRepository.findAll(pageable).forEach(objects::add);
            List<ProductDto> entityDtoList = productDtoUtil.toDtoList(objects);
            subcategoryDtoWithProducts.setTitle(entityFind.getTitle());
            subcategoryDtoWithProducts.setTitleUrl(entityFind.getTitleUrl());
            subcategoryDtoWithProducts.setId(entityFind.getId());
            subcategoryDtoWithProducts.setImageUrl(entityFind.getImageUrl());
            subcategoryDtoWithProducts.setQuantity(entityFind.getQuantity());
            subcategoryDtoWithProducts.setDescription(entityFind.getDescription());
            subcategoryDtoWithProducts.setProducts(entityDtoList);
        }else {
            List<Product> products = productRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey,searchKey,pageable);
            List<ProductDto> entityDtoList = productDtoUtil.toDtoList(products);
            subcategoryDtoWithProducts.setTitle(entityFind.getTitle());
            subcategoryDtoWithProducts.setTitleUrl(entityFind.getTitleUrl());
            subcategoryDtoWithProducts.setId(entityFind.getId());
            subcategoryDtoWithProducts.setImageUrl(entityFind.getImageUrl());
            subcategoryDtoWithProducts.setQuantity(entityFind.getQuantity());
            subcategoryDtoWithProducts.setDescription(entityFind.getDescription());
            subcategoryDtoWithProducts.setProducts(entityDtoList);
        }
        return subcategoryDtoWithProducts;
    }

    @Override
    public List<SubcategoryDto> findByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Категория не найдена!"));
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        for (Subcategory subcategory : category.getSubcategories()) {
            SubcategoryDto subcategoryDto = getEntityDtoUtil().toDto(subcategory);
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
    public SubcategoryDtoWithProducts findByIdWithProducts(Long subcategoryId) {
        Subcategory entityFind = getRepository().findById(subcategoryId).orElseThrow(()->new ResourceNotFoundException("Данная подкатегория не найдена!"));
        return subcategoryDtoWithProductsUtil.toDto(entityFind);
    }

    @Override
    public SubcategoryDtoWithProducts findByTitleUrlWithProducts(String titleUrl) {
        Subcategory entityFind = subcategoryRepository.findByTitleUrl(titleUrl).orElseThrow(()->new ResourceNotFoundException("Данная подкатегория не найдена!"));
        return subcategoryDtoWithProductsUtil.toDto(entityFind);
    }

    @Override
    public List<ProductDto> getProductsInSubcategory(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Подкатегория не найдена!"));
        return productDtoUtil.toDtoList(subcategory.getProducts());
    }
}
