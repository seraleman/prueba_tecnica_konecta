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
import com.seraleman.prueba_tecnica_java.models.Product;
import com.seraleman.prueba_tecnica_java.services.IProductService;

/**
 * Clase controladora de las acciones(CRUD) realizadas sobre la entidad Product
 * (producto)
 */
@RestController
@RequestMapping("/product")
public class ProductRestController {

    // Inyectando interfaz del servicio de Product (producto)
    @Autowired
    private IProductService productService;

    /**
     * Inyectando helper para canalizar las respuesta de cada petición.
     * Todas las respuestas de todas las entidades se canalizan a travéz de la
     * interfaz IResponseDao.
     */
    @Autowired
    private IResponse response;

    /**
     * Crea el endpoint para visualizar todas los productos
     * 
     * @return un objeto tipo HashMap<String, Object>, con el listado de objetos
     *         Product, mensaje de no hay nada en la BD o error en la BD
     */
    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        try {
            List<Product> products = productService.getProducts();
            if (products.isEmpty()) {
                return response.empty();
            }
            return response.list(products);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para visualizar un producto por id
     * 
     * @param id del producto que se quiere visualizar
     * @return una respuesta de la entidad tipo es HasMap<String, Object>,ya sea con
     *         el objeto Product, mensaje de no encontrado o error en
     *         la DB.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return response.notFound(id);
            }
            return response.found(product);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para crear una categoría
     * 
     * @param product tipo Product, es el objeto enviado desde front
     *                para ser creado y persistido.
     * @param result  tipo BindingResult, es el objeto que me permite validar los
     *                campos marcados como obligatorios para crear el objeto.
     */
    @PostMapping("/")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
            BindingResult result) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            return response.created(productService.saveProduct(product).getId());
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para actualizar una categoría
     * 
     * @param product tipo Product, es el objeto enviado desde front
     *                para actualizar el objeto persistido en la BD.
     * @param result  tipo BindingResult, es el objeto que me permite validar
     *                los campos marcados como obligatorios para actualizar el
     *                objeto.
     * @param id      id del objeto a actualizar
     * @return Objeto HashMap<String, Object> con mensaje de objeto actualizado,
     *         objeto no encontrado o error en la BD.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@Valid @RequestBody Product product,
            BindingResult result,
            @PathVariable Long id) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            Product currentProduct = productService.getProductById(id);

            // Se valida que el objeto a actualizar exista
            if (currentProduct == null) {
                response.notFound(id);
            }

            // Se actualiza cada campo del objeto excepto el id
            currentProduct.setCategory(product.getCategory());
            currentProduct.setCreated(product.getCreated());
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setReference(product.getReference());
            currentProduct.setStock(product.getStock());
            currentProduct.setWeight(product.getWeight());

            productService.saveProduct(currentProduct);
            return response.updated();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para eliminar un producto por id
     * 
     * @param id del producto a eliminar
     * @return Objeto HashMap<String, Object> con mensaje de objeto
     *         eliminado o error en la BD
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);

            // Se valida que el objeto a actualizar exista
            if (product == null) {
                return response.notFound(id);
            }

            productService.deleteProductById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

}
