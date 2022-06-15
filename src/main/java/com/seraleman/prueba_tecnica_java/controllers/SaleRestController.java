package com.seraleman.prueba_tecnica_java.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.seraleman.prueba_tecnica_java.models.Sale;
import com.seraleman.prueba_tecnica_java.services.IProductService;
import com.seraleman.prueba_tecnica_java.services.ISaleService;

@RestController
@RequestMapping("/sale")
public class SaleRestController {

    @Autowired
    private ISaleService saleService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IResponse response;

    @GetMapping("/")
    public ResponseEntity<?> getSales() {
        try {
            List<Sale> sales = saleService.getSales();
            if (sales.isEmpty()) {
                return response.empty();
            }
            return response.list(sales);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        try {
            Sale sale = saleService.getSaleById(id);
            if (sale == null) {
                return response.notFound(id);
            }
            return response.found(sale);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        try {
            Product product = productService.getProductById(sale.getProduct().getId());

            if (product.getStock() < sale.getQuantity()) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("message", "No hay suficientes unidades de "
                        .concat(product.getName())
                        .concat(" para la venta.\n Hay ")
                        .concat(String.valueOf(product.getStock()))
                        .concat(" disponible(s)."));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            return response.created(saleService.saveSale(sale));
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSaletById(@RequestBody Sale sale, @PathVariable Long id) {
        try {
            Sale currentSale = saleService.getSaleById(id);
            if (currentSale == null) {
                response.notFound(id);
            }
            currentSale.setCreated(sale.getCreated());
            currentSale.setProduct(sale.getProduct());
            currentSale.setQuantity(sale.getQuantity());

            return response.created(saleService.saveSale(currentSale));
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSaleById(@PathVariable Long id) {
        try {
            Sale sale = saleService.getSaleById(id);
            if (sale == null) {
                return response.notFound(id);
            }
            saleService.deleteSaleById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

}
