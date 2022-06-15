package com.seraleman.prueba_tecnica_java.repositories;

import org.springframework.data.repository.CrudRepository;

import com.seraleman.prueba_tecnica_java.models.Sale;

public interface ISaleDao extends CrudRepository<Sale, Long> {

}
