package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Product;

public interface IProductService {

    List<Product> getProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProductById(Long id);
}
