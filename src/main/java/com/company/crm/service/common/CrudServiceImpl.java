package com.company.crm.service.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    private final JpaRepository<T, ID> repository;

    public CrudServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
