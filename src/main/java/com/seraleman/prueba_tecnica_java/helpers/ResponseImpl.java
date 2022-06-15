package com.seraleman.prueba_tecnica_java.helpers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseImpl implements IResponse {

    private Map<String, Object> response;

    @Override
    public ResponseEntity<Map<String, Object>> errorDataAccess(DataAccessException e) {
        response = new LinkedHashMap<>();
        response.put("message", "Error en la base de datos");
        response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Object>> empty() {
        response = new LinkedHashMap<>();
        response.put("message", "No hay objetos en la lista");
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> list(List<?> objs) {
        response = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        response.put("message", "Lista de objetos disponible");
        data.put("quantity", objs.size());
        data.put("list", objs);
        response.put("data", data);
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> found(Object obj) {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto disponible");
        response.put("data", obj);
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> notFound(Object id) {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto con id '".concat(id.toString()).concat("' no existe en la base de datos"));
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Map<String, Object>> created(Object obj) {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto creado");
        response.put("data", obj);
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleted() {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto eliminado");
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.OK);
    }
}
