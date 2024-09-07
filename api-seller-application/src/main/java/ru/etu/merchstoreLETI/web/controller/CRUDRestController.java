package ru.etu.merchstoreLETI.web.controller;

import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.etu.merchstoreLETI.service.interf.CRUDService;

import java.util.List;

public abstract class CRUDRestController<E,K> {
    abstract CRUDService<E,K> getService();

    @PostMapping("/create")
    public ResponseEntity<E> createEntity(@RequestBody E entityForCreate){
        getService().createEntity(entityForCreate);
        return ResponseEntity.ok(entityForCreate);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<E> findById(@PathVariable K id){
        E object = getService().findById(id);
        if(object==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(object);
    }
    @GetMapping("/all")
    public ResponseEntity<List<E>> findAll(){
        List<E> objects = getService().findAll();
        return ResponseEntity.ok(objects);
    }
    @PutMapping("/update")
    public ResponseEntity<E> updateEntity(@RequestBody E objectForUpdate){
        E updatedObject = getService().updateEntity(objectForUpdate);
        return ResponseEntity.ok(updatedObject);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable K id){
        E objectForDelete = getService().findById(id);
        getService().deleteEntity(objectForDelete);
        return ResponseEntity.noContent().build();
    }
}


