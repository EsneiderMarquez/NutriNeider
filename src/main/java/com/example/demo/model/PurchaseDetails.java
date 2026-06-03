package com.example.demo.model;
import jakarta.persistence.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="PurchaseDetails")
public class PurchaseDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="purchase_id")
    private Purchase purchase;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;
    private double unitPrice;


    public PurchaseDetails() {
    }


    public PurchaseDetails(int id, Purchase purchase, Product product, int quantity, double unitPrice) {
        this.id = id;
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Purchase getPurchase() {
        return purchase;
    }


    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }


    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getUnitPrice() {
        return unitPrice;
    }


    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }



}