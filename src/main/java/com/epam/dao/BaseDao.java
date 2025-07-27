package com.epam.dao;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface BaseDao<T> {
    void save (T entity);
    T findById(long id);
    List<T> findAll();
    void update(T entity);
    void deletedById(long id);
}
