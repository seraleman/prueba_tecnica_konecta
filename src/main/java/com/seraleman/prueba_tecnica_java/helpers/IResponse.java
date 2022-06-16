package com.seraleman.prueba_tecnica_java.helpers;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * Interfaz que me permite agrupar y reutilizar las respuestas de los
 * controladores (endpoints)
 */
public interface IResponse {

    /**
     * Respuesta para cuando hay un error en la base de datos
     * 
     * @param e error atrapado
     * @return HasMap<String, Object> con mensaje de error
     */
    ResponseEntity<Map<String, Object>> errorDataAccess(DataAccessException e);

    /**
     * Respuesta cuando la consulta trae una lista vacía
     * 
     * @return HasMap<String, Object> con mensaje "No hay objetos en la lista"
     */
    ResponseEntity<Map<String, Object>> empty();

    /**
     * Respuesta para devolver la lista de los objetos encontrados
     * 
     * @param objs que serán devueltos a la respuesta
     * @return objeto HasMap<String, Object> con mensaje "Objetos disponible" y los
     *         objetos solicitados
     */
    ResponseEntity<Map<String, Object>> list(List<?> objs);

    /**
     * Respuesta para devolver un objeto encontrado
     * 
     * @param obj que será devuelto a la respuesta
     * @return objeto HasMap<String, Object> con mensaje "Objeto disponible" y el
     *         objeto buscado
     */
    ResponseEntity<Map<String, Object>> found(Object obj);

    /**
     * Respuesta con mensaje de objeto no encontrado
     * 
     * @param id del objeto a buscar
     * @return objeto HasMap<String, Object> con mensaje "Objeto con id <id> no
     *         existe en la BD"
     */
    ResponseEntity<Map<String, Object>> notFound(Object id);

    /**
     * Respuesta para objetos creados
     * 
     * @param id del objeto creado
     * @return objeto HasMap<String, Object> con mensaje "Objeto creado con id <id>"
     */
    ResponseEntity<Map<String, Object>> created(Long id);

    /**
     * Respuesta para objetos actualizados
     * 
     * @return objeto HasMap<String, Object> con mensaje "Objeto actualizado"
     */
    ResponseEntity<Map<String, Object>> updated();

    /**
     * Respuesta cuando se borra un objeto
     * 
     * @return objeto HasMap<String, Object> con mensaje de "Objeto eliminado"
     */
    ResponseEntity<Map<String, Object>> deleted();

    /**
     * Respuesta cuando un objeto no tiene los campos marcados como obligatios
     * (NotNull)
     * 
     * @param result tipo BindingResult con el resultado de la validación de la
     *               existencia de los campos obligatorios
     * @return Un array con los campos faltantes del objeto
     */
    ResponseEntity<Map<String, Object>> invalidObject(BindingResult result);

    /**
     * Respuesta cuando las cantidades de un producto no son suficientes para
     * actualizar una venta.
     * 
     * @return objeto HasMap<String, Object> con mensaje de "No hay cantidad
     *         suficiente"
     */
    ResponseEntity<Map<String, Object>> insufficient();

}
