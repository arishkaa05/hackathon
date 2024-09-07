package ru.etu.merchstoreLETI.service.implementation;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.service.interf.CRUDService;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCRUDService<E,K,L> implements CRUDService<E,K> {
    abstract JpaRepository<L,K> getRepository();
    abstract EntityDtoUtil<E,L> getEntityDtoUtil();
    @Override
    @Transactional
    public void createEntity(E objectDto){
        L entity = getEntityDtoUtil().toEntity(objectDto);
        L entitySave = getRepository().save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public E findById(K id){
        L entityFind = getRepository().findById(id).orElseThrow(()->new ResourceNotFoundException("Данная модель не найдена!"));
        return getEntityDtoUtil().toDto(entityFind);
    }
    @Override
    @Transactional(readOnly = true)
    public List<E> findAll(){
        List<L> objects = new ArrayList<>();
        getRepository().findAll().forEach(objects::add);
        List<E> entityDtoList = getEntityDtoUtil().toDtoList(objects);
        return entityDtoList;
    }
    @Override
    @Transactional
    public E updateEntity(E objectDto){
        L entityUpdate = getEntityDtoUtil().toEntity(objectDto);
        L entitySave = getRepository().save(entityUpdate);
        E dto = getEntityDtoUtil().toDto(entitySave);
        return dto;

    }
    @Override
    @Transactional
    public void deleteEntity(E objectDto){
        L entityForDelete = getEntityDtoUtil().toEntity(objectDto);
        getRepository().delete(entityForDelete);
    }
}

