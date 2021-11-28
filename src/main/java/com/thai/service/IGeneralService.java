package com.thai.service;

import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();

    T findById(Long id);

    T save(T t);

    T update(T t);

    void remove(Long id);
}
