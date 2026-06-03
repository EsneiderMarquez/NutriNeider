package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired PurchaseRepository purchaseRepository;
    @Autowired ProductRepository productRepository;
    @Autowired ClientRepository clientRepository;

    @Transactional
    public Purchase makePurchase(String clientName, String clientEmail, List<Integer> productIds, List<Integer> quantities){

        Optional<Client> optionalClient=clientRepository.findByEmail(clientEmail);

        Client client;
        if(optionalClient.isEmpty()){
            client=new Client();
            client.setName(clientName);
            client.setEmail(clientEmail);
            clientRepository.save(client);
        }else{
            client =optionalClient.get();
        }
        Purchase purchase =new Purchase();
        purchase.setClient(client);
        purchase.setDate(java.time.LocalDateTime.now());
        purchase.setStatus(0);

        double grossTotal=0;
        double totalProtein=0, totalCarbs=0, totalFats=0;

        for (int i = 0; i< productIds.size(); i++){
    
            Optional<Product> optionalProduct = productRepository.findById(productIds.get(i));
            if(optionalProduct.isEmpty()){
                throw new IllegalArgumentException("Product not found with id:" + productIds.get(i));

            }
            Product product =optionalProduct.get();
            int qty = quantities.get(i);
            if (qty<=0){
                continue;
            }
            if (qty>product.getStock()){
                throw new IllegalArgumentException("Not enough stock for: " + product.getName());
            }

            double subtotal = product.getPrice()* qty;
            grossTotal += subtotal;

            totalProtein += product.getProteins() * (product.getGramsPer()/100.0)*qty;
            totalCarbs += product.getCarbohydrates()* (product.getGramsPer()/100.0)*qty;
            totalFats += product.getFats() * (product.getGramsPer()/100.0)*qty;

            PurchaseDetails detail=new PurchaseDetails();
            detail.setPurchase(purchase);
            detail.setProduct(product);
            detail.setQuantity(qty);
            detail.setUnitPrice(product.getPrice());
            //agregar los detalle de la compra a la compra
            purchase.getDetails().add(detail);

            product.setStock(product.getStock()-qty);
            // guardar el nuevo producto con el stock reducido por la compra
            productRepository.save(product);  
        }

        if (purchase.getDetails().isEmpty()) {
        throw new IllegalArgumentException("The purchase must have at least one valid product");
        }

        purchase.setGrossTotal(grossTotal);

        //clasificar y calcular calorias
        double caloriesFromProtein= totalProtein*4;
        double caloriesFromCarbs= totalCarbs *4;
        double caloriesFromFats= totalFats*9;
        double totalCalories= caloriesFromCarbs + caloriesFromFats + caloriesFromProtein;

        String classification="BALANCED";
        double discountPercent=0;

        if (totalCalories>0){
            double proteinPorcent = (caloriesFromProtein/totalCalories)*100;
            double carbsPorcent=(caloriesFromCarbs/totalCalories)*100;
            double fatsPorcent=(caloriesFromFats/totalCalories)*100;
            

            if(proteinPorcent>=35){
                classification="HIGH_PROTEIN";
                discountPercent=0.10;

            }else if(fatsPorcent>=40){
                classification="HIGH_FATS";
                discountPercent=-0.08;

            }else if(carbsPorcent>=50){
                classification="HIGH_CARB";
                discountPercent=0.05;

            }else{
                classification="BALANCED";
                discountPercent=0.15;
            }
        }
        purchase.setLabel(classification);

        double discount = grossTotal*discountPercent;
        double netTotal=grossTotal - discount;

        purchase.setDiscount(discount);
        purchase.setNetTotal(netTotal);
        purchase.setStatus(1);

        return purchaseRepository.save(purchase);

    }

    public List<Purchase> findPurchasesByEmail(String email){
        return purchaseRepository.findByClientEmail(email);
    }
}



