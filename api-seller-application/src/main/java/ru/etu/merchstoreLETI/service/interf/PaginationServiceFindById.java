package ru.etu.merchstoreLETI.service.interf;

import java.util.List;

public interface PaginationServiceFindById<E,K> {
    E findById(int pageNumber,String searchKey,K id);
}
