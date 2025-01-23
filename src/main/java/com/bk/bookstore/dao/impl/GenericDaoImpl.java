package com.bk.bookstore.dao.impl;

import com.bk.bookstore.dao.GenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public abstract class GenericDaoImpl<T, ID> implements GenericDao<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entity;

    protected GenericDaoImpl(Class<T> entity) {
        this.entity = entity;
    }

    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(getEntityManager().find(getEntity(), id));
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery("SELECT e FROM " + getEntity().getSimpleName() + " e", getEntity())
                .getResultList();
    }

    @Override
    public void deleteById(ID id) {
        T entity = getEntityManager().find(getEntity(), id);
        if (entity != null) {
            getEntityManager().remove(entity);
        }
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getEntity() {
        return entity;
    }
}
