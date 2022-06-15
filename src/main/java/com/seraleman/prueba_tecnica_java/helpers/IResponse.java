package com.seraleman.prueba_tecnica_java.helpers;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

public interface IResponse {

    ResponseEntity<Map<String, Object>> errorDataAccess(DataAccessException e);

    ResponseEntity<Map<String, Object>> empty();

    ResponseEntity<Map<String, Object>> list(List<?> objs);

    ResponseEntity<Map<String, Object>> found(Object obj);

    ResponseEntity<Map<String, Object>> notFound(Object id);

    ResponseEntity<Map<String, Object>> created(Object obj);

    ResponseEntity<Map<String, Object>> deleted();

}
