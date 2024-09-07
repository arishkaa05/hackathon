package ru.etu.merchstoreLETI.service.interf;

import org.springframework.security.core.Authentication;
import ru.etu.merchstoreLETI.web.dto.BucketDtoOnlyProducts;
import ru.etu.merchstoreLETI.web.dto.FavoriteItemDto;
import ru.etu.merchstoreLETI.web.dto.FavoriteItemDtoOnlyProducts;

public interface FavoriteItemService extends CRUDService<FavoriteItemDto,Long> {
    FavoriteItemDto findFavoriteItemDtoByUserId(Long userId);

    FavoriteItemDto updateFavoriteItemsProducts(Authentication authentication, FavoriteItemDtoOnlyProducts favoriteItemDtoOnlyProducts);

    void createFavoriteItemWithoutProducts();
}
