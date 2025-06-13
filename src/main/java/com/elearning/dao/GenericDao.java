package com.elearning.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, Id> {

    T save(T entity);

    Optional<T> findById(Id id);

    List<T> findAll();

    void deleteById(Id id);

    void delete(T entity);
}
