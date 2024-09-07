package ru.etu.merchstoreLETI.service.interf;


import java.util.List;

public interface CRUDService<E,K> {
    void createEntity(E entityDto);
    E findById(K id);

    List<E> findAll();
    E updateEntity(E entityDto);

    void deleteEntity(E entityDto);
}
