package com.komsije.booking.service.interfaces.crud;

import com.komsije.booking.model.Host;

import java.util.List;
import java.util.Set;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(ID id);
}
