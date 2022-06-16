package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Category;

/**
 * Interface de servicio en la que se establecen los métodos para manipular la
 * información de la entidad Category
 */
public interface ICategoryService {

    /**
     * Lista todas las categórías de la BD
     * 
     * @return Lista de categorías
     */
    List<Category> getCategories();

    /**
     * Devuelve una categoría en específico
     * 
     * @param id de la categoría que se desea buscar
     * @return un objeto Category
     */
    Category getCategoryById(Long id);

    /**
     * Persiste un objeto Category en la BD
     * 
     * Si el objeto tiene un id, lo actualiza sin crear otro nuevo.
     * 
     * @param category objeto Category para persisitir
     * @return Objeto Category creado o actualizado
     */
    Category saveCategory(Category category);

    /**
     * Elimina un objeto Category por id de la BD
     * 
     * @param id del objeto a eliminar
     */
    void deleteCategoryById(Long id);

}
