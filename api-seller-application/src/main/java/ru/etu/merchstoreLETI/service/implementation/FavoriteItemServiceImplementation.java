package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.FavoriteItem;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.FavoriteItemRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.service.interf.FavoriteItemService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;
import ru.etu.merchstoreLETI.util.FavoriteItemDtoUtil;
import ru.etu.merchstoreLETI.web.dto.FavoriteItemDto;
import ru.etu.merchstoreLETI.web.dto.FavoriteItemDtoOnlyProducts;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteItemServiceImplementation extends AbstractCRUDService<FavoriteItemDto,Long, FavoriteItem> implements FavoriteItemService {
    private final FavoriteItemRepository favoriteItemRepository;
    private final FavoriteItemDtoUtil favoriteItemDtoUtil;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    @Override
    JpaRepository<FavoriteItem, Long> getRepository() {
        return favoriteItemRepository;
    }

    @Override
    EntityDtoUtil<FavoriteItemDto, FavoriteItem> getEntityDtoUtil() {
        return favoriteItemDtoUtil;
    }

    @Override
    public FavoriteItemDto findFavoriteItemDtoByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден!"));
        FavoriteItem favoriteItemForUser = user.getFavoriteItem();
        FavoriteItemDto favoriteItemDto = favoriteItemDtoUtil.toDto(favoriteItemForUser);
        return favoriteItemDto;
    }

    @Override
    @Transactional
    public FavoriteItemDto updateFavoriteItemsProducts(Authentication authentication, FavoriteItemDtoOnlyProducts favoriteItemDtoOnlyProducts) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        FavoriteItem favoriteItem = user.getFavoriteItem();
        List<Product> products = new ArrayList<>();
        for(Long productId: favoriteItemDtoOnlyProducts.getProductIds()){
            Product product = productRepository.findById(productId)
                    .orElseThrow(()->new ResourceNotFoundException("Продукт не найден с id: "+productId));
            products.add(product);
        }
        favoriteItem.setProducts(products);
        favoriteItemRepository.save(favoriteItem);
        FavoriteItemDto favoriteItemDto = favoriteItemDtoUtil.toDto(favoriteItem);
        return favoriteItemDto;
    }

    @Override
    public void createFavoriteItemWithoutProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        FavoriteItem favoriteItem = new FavoriteItem();
        favoriteItem.setUser(user);
        favoriteItemRepository.save(favoriteItem);
    }
}
