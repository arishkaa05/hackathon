package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.*;
import ru.etu.merchstoreLETI.repository.BucketRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.service.interf.BucketService;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.util.BucketDtoUtil;
import ru.etu.merchstoreLETI.util.BucketDtoWithProductsUtil;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;
import ru.etu.merchstoreLETI.util.ProductDtoUtil;
import ru.etu.merchstoreLETI.web.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketServiceImplementation extends AbstractCRUDService<BucketDto,Long,Bucket> implements BucketService, PaginationService<BucketDto,Long> {
   private final BucketRepository bucketRepository;
   private final BucketDtoUtil bucketDtoUtil;
   private final ProductService productService;
   private final ProductDtoUtil productDtoUtil;
   private final ProductRepository productRepository;
   private final UserRepository userRepository;
   private final UserService userService;
   private final BucketDtoWithProductsUtil bucketDtoWithProductsUtil;
    @Override
    JpaRepository<Bucket, Long> getRepository() {
        return bucketRepository;
    }

    @Override
    EntityDtoUtil<BucketDto, Bucket> getEntityDtoUtil() {
        return bucketDtoUtil;
    }

    @Override
    @Transactional
    public void createEntity(BucketDto objectDto) {
        Bucket bucket = getEntityDtoUtil().toEntity(objectDto);
        List<Product> saveProducts = new ArrayList<>();
        for (Long productId : objectDto.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт с данным id: " + productId + " не найден"));
            saveProducts.add(product);
        }
        bucket.setProducts(saveProducts);
        User user = userRepository.findById(objectDto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Пользователь с данным id: "+objectDto.getUserId()+" не найден"));
        bucket.setUser(user);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional(readOnly = true)
    public BucketDto findById(Long id) {
        Bucket entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная корзина не найдена!"));
        BucketDto bucketDto =getEntityDtoUtil().toDto(entityFind);
        List<Long> productIdsFind = new ArrayList<>();
        for(Product product: entityFind.getProducts()){
            productIdsFind.add(product.getId());
        }
        bucketDto.setId(entityFind.getId());
        bucketDto.setUserId(entityFind.getUser().getId());
        bucketDto.setProductIds(productIdsFind);
        return bucketDto;
    }

    @Override
    @Transactional
    public BucketDto updateEntity(BucketDto objectDto) {
        Bucket existingBucket = bucketRepository.findById(objectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Корзина не найдена!"));
        List<Product> updatedProducts = new ArrayList<>();
        for (Long productId : objectDto.getProductIds()) {
            ProductDto productDto = productService.findById(productId);
            if (productDto != null) {
                Product savedProduct = productDtoUtil.toEntity(productDto);
                updatedProducts.add(savedProduct);
            } else {
                throw new ResourceNotFoundException("Продукт с данным ID: " + productId + " не найден!");
            }
        }
        existingBucket.setProducts(updatedProducts);
        Bucket updatedBucket = bucketRepository.save(existingBucket);
        BucketDto bucketDto = bucketDtoUtil.toDto(updatedBucket);
        List<Long> productIdsFind = new ArrayList<>();
        for (Product product : updatedBucket.getProducts()) {
            productIdsFind.add(product.getId());
        }
        bucketDto.setProductIds(productIdsFind);
        return bucketDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BucketDto> findAll() {
        List<Bucket> buckets = getRepository().findAll();
        List<BucketDto> bucketDtos = new ArrayList<>();
        for(Bucket bucket: buckets){
            BucketDto bucketDto = getEntityDtoUtil().toDto(bucket);
            bucketDto.setUserId(bucket.getUser().getId());
            List<Long> productIds = bucket
                    .getProducts()
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            bucketDto.setProductIds(productIds);
            bucketDtos.add(bucketDto);
        }
        return bucketDtos ;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BucketDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if (searchKey.equals("")) {
            Page<Bucket> buckets = getRepository().findAll(pageable);
            List<BucketDto> bucketDtos = new ArrayList<>();
            for (Bucket bucket : buckets) {
                BucketDto bucketDto = getEntityDtoUtil().toDto(bucket);
                bucketDto.setUserId(bucket.getUser().getId());
                List<Long> productIds = bucket
                        .getProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
                bucketDto.setProductIds(productIds);
                bucketDtos.add(bucketDto);
            }
            return bucketDtos;
        }else {
            List<Bucket> buckets = bucketRepository.findByTotalCostContainingIgnoreCaseOrAmountProductsContainingIgnoreCase(
                    searchKey,searchKey,pageable);
            List<BucketDto> bucketDtos = new ArrayList<>();
            for (Bucket bucket : buckets) {
                BucketDto bucketDto = getEntityDtoUtil().toDto(bucket);
                bucketDto.setUserId(bucket.getUser().getId());
                List<Long> productIds = bucket
                        .getProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
                bucketDto.setProductIds(productIds);
                bucketDtos.add(bucketDto);
            }
            return bucketDtos;
        }
    }

    @Override
    @Transactional
    public BucketDtoWithProducts updateBucketProducts(Authentication authentication, BucketDtoOnlyProducts bucketDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        Bucket bucket = user.getBucket();
        List<Product> productsNew = new ArrayList<>();
        for(Long productId: bucketDto.getProductIds()){
            Product product = productRepository.findById(productId)
                    .orElseThrow(()->new ResourceNotFoundException("Продукт не найден!"));
            productsNew.add(product);
        }
        bucket.setProducts(productsNew);
        return bucketDtoWithProductsUtil.toDto(bucket);
    }

    @Override
    @Transactional
    public void createBucketWithoutProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDtoWithProducts findBucketByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Пользователь с данным id не найден!"));
        Bucket bucket = user.getBucket();
        BucketDtoWithProducts bucketDtoWithProducts = bucketDtoWithProductsUtil.toDto(bucket);
        return bucketDtoWithProducts;
    }
}
