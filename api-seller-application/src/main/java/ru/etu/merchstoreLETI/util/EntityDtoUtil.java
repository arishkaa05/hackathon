package ru.etu.merchstoreLETI.util;

import java.util.List;

public interface EntityDtoUtil<E,L> {
    E toDto(L entityModel);
    L toEntity(E entityModelDto);
    List<E> toDtoList(List<L> entityModelList);
    List<L> toEntityList(List<E> entityModelDtoList);
}

