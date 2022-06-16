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

/**
 * Clase controladora de las acciones(CRUD) realizadas sobre la entidad Sale
 * (Venta)
 */
@RestController
@RequestMapping("/sale")
public class SaleRestController {

    // Inyectando interfaz del servicio de Sale (venta)
    @Autowired
    private ISaleService saleService;

    // Inyectando interfaz del servicio de Product (producto)
    @Autowired
    private IProductService productService;

    /**
     * Inyectando helper para canalizar las respuesta de cada petición.
     * En este entidad, la maypría de las respuestas de las entidades se
     * canalizan a travéz de la interfaz IResponseDao.
     */
    @Autowired
    private IResponse response;

    /**
     * Crea el endpoint para visualizar todas las ventas
     * 
     * @return un objeto tipo HashMap<String, Object>, con el listado de objetos
     *         Sale, mensaje de no hay nada en la BD o error en la BD
     */
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

    /**
     * Crea el endpoint para visualizar una venta por id
     * 
     * @param id de la venta que se quiere visualizar
     * @return una respuesta de la entidad tipo es HasMap<String, Object>,ya sea con
     *         el objeto Sale, mensaje de no encontrado o error en
     *         la DB.
     */
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

    /**
     * Crea el endpoint para crear una venta
     * 
     * @param sale   tipo Sale, es el objeto enviado desde front
     *               para ser creado y persistido.
     * @param result tipo BindingResult, es el objeto que me permite validar los
     *               campos marcados como obligatorios para crear el objeto.
     */
    @PostMapping("/")
    public ResponseEntity<?> createSale(@Valid @RequestBody Sale sale,
            BindingResult result) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }

        try {
            /**
             * Se consulta el producto a persistirse en la venta con el objetivo de poder
             * acceder a la cantidad que hay de este en stock.
             */
            Product currentProduct = productService.getProductById(sale.getProduct().getId());
            Map<String, Object> response = new LinkedHashMap<>();

            // Se compara la cantidad que se intenta vender con la cantidad que hay en stock
            if (currentProduct.getStock() < sale.getQuantity()) {

                // Si no hay las unidades suficientes, se devuelve un mensaje informándolo
                response.put("message", "No hay suficientes unidades de "
                        .concat(currentProduct.getName())
                        .concat(" para la venta, hay ")
                        .concat(String.valueOf(currentProduct.getStock()))
                        .concat(" disponible(s)."));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            /**
             * Cuando hay las unidades suficientes, se establece el nuevo stock del producto
             * descontándole las unidades de la nueva venta y se persiste
             */
            currentProduct.setStock(currentProduct.getStock() - sale.getQuantity());
            productService.saveProduct(currentProduct);

            // Se retorna mensaje con la venta creada
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

    /**
     * Crea el endpoint para actualizar una venta
     * 
     * La actualización de la venta genera la necesidad de persistir los valores
     * adecuados en el stock de cada producto afectado.
     * 
     * Existe las posibilidades de que la actualización de la venta sea con el mismo
     * producto o no, la lógica de cada caso se explica más adelante.
     * 
     * @param sale   tipo Sale, es el objeto enviado desde front
     *               para actualizar el objeto persistido en la BD.
     * @param result tipo BindingResult, es el objeto que me permite validar
     *               los campos marcados como obligatorios para actualizar el
     *               objeto.
     * @param id     id del objeto a actualizar
     * @return Objeto HashMap<String, Object> con mensaje de objeto actualizado,
     *         objeto no encontrado o error en la BD.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSaletById(@Valid @RequestBody Sale sale,
            BindingResult result,
            @PathVariable Long id) {

        // Se valida si al objeto enviado le faltan campos marcados como obligatorios
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }

        try {
            Sale currentSale = saleService.getSaleById(id);

            // Se valida que el objeto a actualizar exista
            if (currentSale == null) {
                return response.notFound(id);
            }

            // Se consulta el producto que se persistirá en la actualización de la venta
            Product currentProduct = productService.getProductById(sale.getProduct().getId());

            /**
             * El condicional externo evalua que el producto que se actualizara en la venta
             * es el mismo que el de la venta persistida
             */
            if (currentSale.getProduct().getId() == sale.getProduct().getId()) {

                /**
                 * Cuando el producto es el mismo, se suma la cantidad de la venta persistida
                 * con la cantidad del producto en stock y se evalua con la nueva cantidad
                 * solicitada.
                 */
                if ((currentProduct.getStock() + currentSale.getQuantity()) < sale.getQuantity()) {

                    // Cuando no es suficiente se devuelve el mensaje con la información.
                    return response.insufficient();
                } else {

                    /**
                     * Cuando es suficiente se suma la cantidad en stock con la cantidad de la venta
                     * persistida actual y se resta la nueva cantidad solicitada. El valor
                     * resultante se persiste como nuevo valor en el stock del producto.
                     */
                    currentProduct.setStock(
                            (currentProduct.getStock() + currentSale.getQuantity()) - sale.getQuantity());
                    productService.saveProduct(currentProduct);
                }

            } else {
                /**
                 * Cuando no es el mismo producto, se evalua la cantidad del nuevo producto en
                 * stock con la cantidad solicitada.
                 */
                if (currentProduct.getStock() < sale.getQuantity()) {

                    // Si la cantidad no es suficiente se devuelve un mensaje con la información.
                    return response.insufficient();
                } else {

                    /**
                     * Cuando la cantidad es suficiente, se accede al producto que actualmente
                     * persiste para devolverle la cantidad al stock.
                     */
                    Product previousProduct = productService.getProductById(currentSale.getProduct().getId());
                    previousProduct.setStock(previousProduct.getStock() + currentSale.getQuantity());
                    productService.saveProduct(previousProduct);

                    /**
                     * Se accede al producto que quedará persistido en la actualización de la venta
                     * para modificar su stock.
                     */
                    currentProduct.setStock(currentProduct.getStock() - sale.getQuantity());
                    productService.saveProduct(currentProduct);
                }
            }

            // Se actualizan los campos de la venta y se persiste.
            currentSale.setQuantity(sale.getQuantity());
            currentSale.setCreated(sale.getCreated());
            currentSale.setProduct(sale.getProduct());

            saleService.saveSale(currentSale);

            return response.updated();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    /**
     * Crea el endpoint para eliminar una venta
     * 
     * La eliminación de una venta genera que las cntidades del producto sean
     * devueltas al stock
     * 
     * @param id de la venta a eliminar
     * @return Objeto HashMap<String, Object> con mensaje de objeto
     *         eliminado o error en la BD
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSaleById(@PathVariable Long id) {
        try {
            Sale sale = saleService.getSaleById(id);

            // Se valida que el objeto a actualizar exista
            if (sale == null) {
                return response.notFound(id);
            }

            /**
             * Se accede al producto que contiene la venta que se va a eliminar, se
             * determinan las cantidades y estas son devueltas al stock del artículo.
             */
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
