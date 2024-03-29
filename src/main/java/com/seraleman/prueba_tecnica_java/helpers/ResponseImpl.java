package com.seraleman.prueba_tecnica_java.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Esta clase implementa la interface IResponse
 */
@Component
public class ResponseImpl implements IResponse {

    // Se declara el atributo response que se será el enviado en tora respuesta.
    private Map<String, Object> response;

    @Override
    public ResponseEntity<Map<String, Object>> errorDataAccess(DataAccessException e) {
        response = new LinkedHashMap<>();
        response.put("message", "Error en la base de datos");
        response.put("error", e.getMessage()
                .concat(": ".concat(e.getMostSpecificCause().getMessage())));
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
        response.put("message", "Objeto con id '"
                .concat(id.toString()).concat("' no existe en la base de datos"));
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Map<String, Object>> created(Long id) {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto creado con id " + id);
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updated() {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto actualizado");
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleted() {
        response = new LinkedHashMap<>();
        response.put("message", "Objeto eliminado");
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> invalidObject(BindingResult result) {
        response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        for (FieldError err : result.getFieldErrors()) {
            errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
        }
        response.put("errors", errors);
        return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Map<String, Object>> insufficient() {
        response = new HashMap<>();
        response.put("message", "No hay suficientes unidades para actualizar la venta");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

    }

}
