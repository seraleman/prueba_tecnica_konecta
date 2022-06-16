package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Product;

/**
 * Interface de servicio en la que se establecen los métodos para manipular la
 * información de la entidad Product
 */
public interface IProductService {

    /**
     * Lista todas las productos de la BD
     * 
     * @return Lista de productos
     */
    List<Product> getProducts();

    /**
     * Devuelve un producto en específico
     * 
     * @param id del producto que se desea buscar
     * @return un objeto Product
     */
    Product getProductById(Long id);

    /**
     * Persiste un objeto Product en la BD
     * 
     * Si el objeto tiene un id, lo actualiza sin crear otro nuevo.
     * 
     * @param product objeto Product para persisitir
     * @return Objeto Product creado o actualizado
     */
    Product saveProduct(Product product);

    /**
     * Elimina un objeto Product por id de la BD
     * 
     * @param id del objeto a eliminar
     */
    void deleteProductById(Long id);
}
