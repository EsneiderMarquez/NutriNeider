package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;


@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double proteins;
    private double carbohydrates;
    private double fats;
    private String description;
    private double price;
    private int stock;
    private double gramsPer;
    private String unityT;
    private String category;
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createTime;


    public Product() {
    }

    
   
    

    public Product( String name, double proteins, double carbohydrates, double fats, String description,
            double price, int stock, double gramsPer, String unityT, String category, Boolean active, LocalDateTime createTime) {
        this.name = name;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.gramsPer = gramsPer;
        this.unityT = unityT;
        this.category = category;
        this.createTime = createTime;
        this.active=active;
    }




    @PrePersist
    public void prePersist(){
        this.createTime=LocalDateTime.now();

    }
    @Transient
    public double getCalories() {
        double factor=gramsPer/100.0;
        double before=((proteins * 4) + (carbohydrates * 4) + (fats * 9))*factor;
        return Math.round(before*100.0)/100.0;
    }




    public int getId() {
        return id;
    }




    public void setId(int id) {
        this.id = id;
    }







    public String getName() {
        return name;
    }







    public void setName(String name) {
        this.name = name;
    }







    public double getProteins() {
        return proteins;
    }







    public void setProteins(double protein) {
        this.proteins = protein;
    }







    public double getCarbohydrates() {
        return carbohydrates;
    }







    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }







    public double getFats() {
        return fats;
    }







    public void setFats(double fats) {
        this.fats = fats;
    }







    public String getDescription() {
        return description;
    }







    public void setDescription(String description) {
        this.description = description;
    }







    public double getPrice() {
        return price;
    }







    public void setPrice(double price) {
        this.price = price;
    }







    public int getStock() {
        return stock;
    }







    public void setStock(int stock) {
        this.stock = stock;
    }







    public double getGramsPer() {
        return gramsPer;
    }







    public void setGramsPer(double gramsPer) {
        this.gramsPer = gramsPer;
    }







    public String getUnityT() {
        return unityT;
    }







    public void setUnityT(String unityT) {
        this.unityT = unityT;
    }







    public String getCategory() {
        return category;
    }







    public void setCategory(String category) {
        this.category = category;
    }







    public LocalDateTime getCreateTime() {
        return createTime;
    }







    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }







    public boolean isActive() {
        return active;
    }







    public void setActive(boolean active) {
        this.active = active;
    }    



}

