package com.seraleman.prueba_tecnica_java.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * En esta entidad se abstrae la clase producto
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Este campo representa el "Nombre del producto"
    private String name;

    // Este campo representa la "Referencia del producto"
    private String reference;

    // Este campo representa el "Precio" del producto
    private Integer price;

    // Este campo representa el peso del producto
    private Integer weight;

    /**
     * Este campo representa la "Categoría" del producto
     * Tiene una relación de muchas categorías para un producto
     */
    @ManyToOne
    private Category category;

    // Este campor representa el "Stock" del producto
    private Integer stock;

    // Este campo representa la "Fecha de creación" del producto
    private Date created;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
