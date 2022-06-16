package com.seraleman.prueba_tecnica_java.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seraleman.prueba_tecnica_java.helpers.IResponse;
import com.seraleman.prueba_tecnica_java.models.Category;
import com.seraleman.prueba_tecnica_java.services.ICategoryService;

/**
 * Clase controladora de las acciones(CRUD) realizadas sobre la entidad Category
 * (Categoría)
 */
@RestController
@RequestMapping("/category")
public class CategoryRestController {

    // Inyectando interfaz del servicio de Category (categoría)
    @Autowired
    private ICategoryService categoryService;

    /**
     * Inyectando helper para canalizar las respuesta de cada petición.
     * Todas las respuestas de todas las entidades se canalizan a travéz de la
     * interfaz IResponseDao.
     */
    @Autowired
    private IResponse response;

    /**
     * Crea el endpoint para visualizar todas las categorías
     * 
     * @return un objeto tipo HashMap<String, Object>, con el listado de objetos
     *         Category, mensaje de no hay nada en la BD o error en la BD
     */
    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            if (categories.isEmpty()) {
                return response.empty();
            }
            return response.list(categories);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para visualizar una categoría por id
     * 
     * @param id de la categoria que se quiere visualizar
     * @return una respuesta de la entidad tipo es HasMap<String, Object>,ya sea con
     *         el objeto Category, mensaje de no encontrado o error en
     *         la DB.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return response.notFound(id);
            }
            return response.found(category);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para crear una categoría
     * 
     * @param category tipo Category, es el objeto enviado desde front
     *                 para ser creado y persistido.
     * @param result   tipo BindingResult, es el objeto que me permite validar los
     *                 campos marcados como obligatorios para crear el objeto.
     */
    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category,
            BindingResult result) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            return response.created(categoryService.saveCategory(category).getId());
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para actualizar una categoría
     * 
     * @param category tipo Category, es el objeto enviado desde front
     *                 para actualizar el objeto persistido en la BD.
     * @param result   tipo BindingResult, es el objeto que me permite validar
     *                 los campos marcados como obligatorios para actualizar el
     *                 objeto.
     * @param id       id del objeto a actualizar
     * @return Objeto HashMap<String, Object> con mensaje de objeto actualizado,
     *         objeto no encontrado o error en la BD.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@Valid @RequestBody Category category,
            BindingResult result,
            @PathVariable Long id) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            Category currentCategory = categoryService.getCategoryById(id);

            // Se valida que el objeto a actualizar exista
            if (currentCategory == null) {
                response.notFound(id);
            }
            // Se actualiza cada campo del objeto excepto el id
            currentCategory.setName(category.getName());
            categoryService.saveCategory(currentCategory);
            return response.updated();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para eliminar una categoría por id
     * 
     * @param id de la categoría a eliminar
     * @return Objeto HashMap<String, Object> con mensaje de objeto
     *         eliminado o error en la BD
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);

            // Se valida que el objeto a actualizar exista
            if (category == null) {
                return response.notFound(id);
            }

            categoryService.deleteCategoryById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }
}
