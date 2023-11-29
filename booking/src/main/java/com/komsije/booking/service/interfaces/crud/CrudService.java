package com.komsije.booking.service.interfaces.crud;

import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id) throws ElementNotFoundException;

    T save(T object) throws ElementNotFoundException;

    T update(T object) throws ElementNotFoundException;

    void delete(ID id) throws HasActiveReservationsException, ElementNotFoundException;
}
