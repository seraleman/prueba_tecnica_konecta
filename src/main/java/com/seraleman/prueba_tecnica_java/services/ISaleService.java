package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Sale;

public interface ISaleService {

    List<Sale> getSales();

    Sale getSaleById(Long id);

    Sale saveSale(Sale sale);

    void deleteSaleById(Long id);
}
