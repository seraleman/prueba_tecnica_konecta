package com.seraleman.prueba_tecnica_java.repositories;

import org.springframework.data.repository.CrudRepository;

import com.seraleman.prueba_tecnica_java.models.Category;

/*
 * Interfaz que implementa todas las sentencias SQL necesarias para realizar 
 * persistencias y consultas a la BD
 */
public interface ICategoryDao extends CrudRepository<Category, Long> {

}
