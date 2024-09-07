package ru.etu.merchstoreLETI.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.web.dto.BucketDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class BucketDtoUtil implements EntityDtoUtil<BucketDto, Bucket> {
    @Override
    public  BucketDto toDto(Bucket entityModel) {
        BucketDto dto = new BucketDto();
        BeanUtils.copyProperties(entityModel,dto);
        return dto;
    }

    @Override
    public  Bucket toEntity(BucketDto entityModelDto) {
        Bucket bucket = new Bucket();
        BeanUtils.copyProperties(entityModelDto,bucket);
        return bucket;
    }

    @Override
    public List<BucketDto> toDtoList(List<Bucket> entityModelList) {
        List<BucketDto> bucketModelDtos = new ArrayList<>();
        for(Bucket model : entityModelList){
            BucketDto dto = toDto(model);
            bucketModelDtos.add(dto);
        }
        return bucketModelDtos;
    }

    @Override
    public List<Bucket> toEntityList(List<BucketDto> entityModelDtoList) {
        List<Bucket> bucketModels = new ArrayList<>();
        for(BucketDto model : entityModelDtoList){
            Bucket modelAdd = toEntity(model);
            bucketModels.add(modelAdd);
        }
        return bucketModels;
    }
}
