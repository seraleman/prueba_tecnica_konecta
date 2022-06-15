package com.seraleman.prueba_tecnica_java.repositories;

import org.springframework.data.repository.CrudRepository;

import com.seraleman.prueba_tecnica_java.models.Category;

public interface ICategoryDao extends CrudRepository<Category, Long> {

}
