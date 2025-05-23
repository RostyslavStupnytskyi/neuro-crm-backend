package com.company.crm.service.common;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T save(T entity);
    T findById(ID id);
    void delete(ID id);
}
