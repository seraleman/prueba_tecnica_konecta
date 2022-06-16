package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Sale;

/**
 * Interface de servicio en la que se establecen los métodos para manipular la
 * información de la entidad Sale
 */
public interface ISaleService {

    /**
     * Lista todas las ventas de la BD
     * 
     * @return Lista de ventas
     */
    List<Sale> getSales();

    /**
     * Devuelve una venta en específica
     * 
     * @param id de la venta que se desea buscar
     * @return un objeto Sale
     */
    Sale getSaleById(Long id);

    /**
     * Persiste un objeto Sale en la BD
     * 
     * Si el objeto tiene un id, lo actualiza sin crear otro nuevo.
     * 
     * @param sale objeto Category para persisitir
     * @return Objeto Sale creado o actualizado
     */
    Sale saveSale(Sale sale);

    /**
     * Elimina un objeto Sale por id dela BD
     * 
     * @param id del objeto a eliminar
     */
    void deleteSaleById(Long id);
}
