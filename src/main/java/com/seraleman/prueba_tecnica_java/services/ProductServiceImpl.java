package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seraleman.prueba_tecnica_java.models.Product;
import com.seraleman.prueba_tecnica_java.repositories.IProductDao;

/**
 * Clase que implementa IProductService
 */
@Service
public class ProductServiceImpl implements IProductService {

    /**
     * Se inyecta la interface DAO (Data Acces Object) de Product para tener acceso
     * a sus métodos
     */
    @Autowired
    private IProductDao productDao;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productDao.deleteById(id);
    }

}
