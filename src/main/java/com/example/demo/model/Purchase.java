package com.example.demo.model;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="Purchase")
public class Purchase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy = "purchase", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetails> details= new java.util.ArrayList<>();
    private LocalDateTime date;
    private int status;
    private double grossTotal;
    private double discount;
    private double netTotal;
    private String label;

    
    public Purchase() {
    }

    public Purchase(int id, Client client, LocalDateTime date, int status, double grossTotal, double discount, double netTotal,
            String label) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.status = status;
        this.grossTotal = grossTotal;
        this.discount = discount;
        this.netTotal = netTotal;
        this.label = label;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }



    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public double getGrossTotal() {
        return grossTotal;
    }


    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }


    public double getDiscount() {
        return discount;
    }


    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public double getNetTotal() {
        return netTotal;
    }


    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<PurchaseDetails> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseDetails> details) {
        this.details = details;
    }
    



}