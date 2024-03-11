package org.sbsa.deco.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CrudService<T, ID> {

    Page<T> findAll(Pageable pageable);

    T findById(ID id);

    boolean existsById(ID var1);

    T save(T object);

    long count();

    void delete(T object);

    void deleteById(ID id);
}
