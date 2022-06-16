package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seraleman.prueba_tecnica_java.models.Sale;
import com.seraleman.prueba_tecnica_java.repositories.ISaleDao;

/**
 * Clase que implementa ISaleService
 */
@Service
public class SaleServiceImpl implements ISaleService {

    /**
     * Se inyecta la interface DAO (Data Acces Object) de Sale para tener acceso
     * a sus métodos
     */
    @Autowired
    private ISaleDao saleDao;

    @Override
    public List<Sale> getSales() {
        return (List<Sale>) saleDao.findAll();
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleDao.findById(id).orElse(null);
    }

    @Override
    public Sale saveSale(Sale sale) {
        return saleDao.save(sale);
    }

    @Override
    public void deleteSaleById(Long id) {
        saleDao.deleteById(id);
    }

}
