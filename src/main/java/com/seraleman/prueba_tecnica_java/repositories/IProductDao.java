package com.seraleman.prueba_tecnica_java.repositories;

import org.springframework.data.repository.CrudRepository;

import com.seraleman.prueba_tecnica_java.models.Product;

/*
 * Interfaz que implementa todas las sentencias SQL necesarias para realizar 
 * persistencias y consultas a la BD
 */
public interface IProductDao extends CrudRepository<Product, Long> {

}
