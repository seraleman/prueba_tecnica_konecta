package com.seraleman.prueba_tecnica_java.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createSale(@Valid @RequestBody Sale sale, BindingResult result) {
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            Product currentProduct = productService.getProductById(sale.getProduct().getId());
            Map<String, Object> response = new LinkedHashMap<>();

            if (currentProduct.getStock() < sale.getQuantity()) {
                response.put("message", "No hay suficientes unidades de "
                        .concat(currentProduct.getName())
                        .concat(" para la venta, hay ")
                        .concat(String.valueOf(currentProduct.getStock()))
                        .concat(" disponible(s)."));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            currentProduct.setStock(currentProduct.getStock() - sale.getQuantity());
            productService.saveProduct(currentProduct);

            response.put("message", "Ha(n) sido vendida(s) "
                    .concat(String.valueOf(sale.getQuantity()))
                    .concat(" unidad(es) de ")
                    .concat(currentProduct.getName())
                    .concat(". Venta creada con id ")
                    .concat(String.valueOf(saleService.saveSale(sale).getId())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSaletById(@Valid @RequestBody Sale sale, BindingResult result,
            @PathVariable Long id) {

        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            Sale currentSale = saleService.getSaleById(id);
            if (currentSale == null) {
                return response.notFound(id);
            }

            Product currentProduct = productService.getProductById(sale.getProduct().getId());
            Map<String, Object> resp = new LinkedHashMap<>();

            if (currentSale.getProduct().getId() == sale.getProduct().getId()) {
                if ((currentProduct.getStock() + currentSale.getQuantity()) < sale.getQuantity()) {
                    resp.put("message", "No hay suficientes unidades para actualizar la venta");
                    return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
                } else {
                    currentProduct.setStock(
                            (currentProduct.getStock() + currentSale.getQuantity()) - sale.getQuantity());
                    productService.saveProduct(currentProduct);
                }
            } else {
                if (currentProduct.getStock() < sale.getQuantity()) {
                    resp.put("message", "No hay suficientes unidades para actualizar la venta");
                    return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
                } else {
                    Product previousProduct = productService.getProductById(currentSale.getProduct().getId());
                    previousProduct.setStock(previousProduct.getStock() + currentSale.getQuantity());

                    currentProduct.setStock(currentProduct.getStock() - sale.getQuantity());
                }
            }

            currentSale.setQuantity(sale.getQuantity());
            currentSale.setCreated(sale.getCreated());
            currentSale.setProduct(sale.getProduct());

            saleService.saveSale(currentSale);

            return response.updated();
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

            Product product = productService.getProductById(sale.getProduct().getId());
            product.setStock(product.getStock() + sale.getQuantity());
            productService.saveProduct(product);

            saleService.deleteSaleById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

}
