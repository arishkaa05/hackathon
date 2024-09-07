package ru.etu.merchstoreLETI.service.interf;

import org.springframework.security.core.Authentication;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.web.dto.BucketDto;
import ru.etu.merchstoreLETI.web.dto.BucketDtoOnlyProducts;
import ru.etu.merchstoreLETI.web.dto.BucketDtoWithProducts;

public interface BucketService extends CRUDService<BucketDto,Long>,PaginationService<BucketDto,Long> {
    BucketDtoWithProducts updateBucketProducts(Authentication authentication, BucketDtoOnlyProducts bucketDto);

    void createBucketWithoutProducts();

    BucketDtoWithProducts findBucketByUserId(Long userId);
}
