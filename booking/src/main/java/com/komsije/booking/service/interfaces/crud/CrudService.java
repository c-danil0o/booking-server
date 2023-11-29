package com.komsije.booking.service.interfaces.crud;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T object);

    T update(T object);

    void delete(ID id);
}
