package ru.etu.merchstoreLETI.web.mappers;

import org.mapstruct.Mapper;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.web.dto.BucketDto;
@Mapper(componentModel = "spring")
public interface BucketMapper extends Mappable<Bucket, BucketDto> {
}
