package com.seraleman.prueba_tecnica_java.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Este entidad abstrae un módulo de venta.
 */
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Este campo representa la fecha de creación de la venta
    @NotNull
    private Date created;

    /**
     * Este campo representa el producto vendido
     * Tiene una relación de un producto para una venta
     * 
     * Nota: Me apego a lo que solicita la prueba,
     * la cual dice que en cada venta se vende un solo producto.
     */
    @OneToOne
    @NotNull
    private Product product;

    // Este campor representa la cantidad del producto vendido
    @NotNull
    private Integer quantity;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
