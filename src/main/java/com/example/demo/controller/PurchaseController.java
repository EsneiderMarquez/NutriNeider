package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Purchase;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class PurchaseController {
    @Autowired private PurchaseService purchaseService;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ProductRepository productRepository;


    //metodo para get para mandar todos los productos y los clientes para poder buscarlos al ponerlos para hacer una compra
    @GetMapping("/purchase/buy")
    public String Buy(Model model) {
        model.addAttribute("products", productRepository.findByActiveTrue());
        model.addAttribute("clients", clientRepository.findAll());
        
        return "index";
    }

    //Metodo post para guardar las compras en la base de datos
    @PostMapping("/purchase/create")
    public String makePurchase(@RequestParam String clientName, @RequestParam String clientEmail,@RequestParam List<Integer> productIds, @RequestParam List<Integer> quantities, RedirectAttributes ra) {

        boolean hasProductSelected = false;

        for (Integer q : quantities) {
            if (q != null && q > 0) {
                hasProductSelected = true;
            }
        }

        if (!hasProductSelected) {
            ra.addFlashAttribute("purchaseError", "Got to choose at least one product");
            return "redirect:/";
        }

        try{
            Purchase purchase = purchaseService.makePurchase(clientName, clientEmail, productIds, quantities);
            ra.addFlashAttribute("purchaseSuccess", "true");
            ra.addFlashAttribute("purchaseTotal", purchase.getNetTotal());
            ra.addFlashAttribute("purchaseDiscount", purchase.getDiscount());
            ra.addFlashAttribute("purchaseLabel", purchase.getLabel());
        }catch(Exception e){
            ra.addFlashAttribute("purchaseError",e.getMessage());
        }
                
        return "redirect:/";

    }
    @GetMapping("/purchase/my")
    public String myPurchases(@RequestParam String myEmail, Model model) {
        model.addAttribute("myPurchases", purchaseService.findPurchasesByEmail(myEmail));
        model.addAttribute("myEmail", myEmail);
        model.addAttribute("products", productRepository.findByActiveTrue());
        return "index";
    }
    
    
}
