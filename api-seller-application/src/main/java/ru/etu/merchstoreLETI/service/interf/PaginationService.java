package ru.etu.merchstoreLETI.service.interf;

import java.util.List;

public interface PaginationService<E,K> {
    List<E> findAll(int pageNumber,String searchKey);
}
