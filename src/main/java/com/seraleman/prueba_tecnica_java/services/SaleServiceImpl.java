package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.seraleman.prueba_tecnica_java.models.Sale;
import com.seraleman.prueba_tecnica_java.repositories.ISaleDao;

public class SaleServiceImpl implements ISaleService {

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
