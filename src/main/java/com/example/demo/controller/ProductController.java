
package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;





@Controller

public class ProductController {
    @Autowired
    private ProductRepository repository;
    
    
    @PostMapping("/product/insert")
    public String insert(@RequestParam String name, @RequestParam Double proteins, @RequestParam Double carbohydrates
        , @RequestParam Double fats, @RequestParam String description, @RequestParam Double price, @RequestParam int stock
        , @RequestParam Double gramsPer, @RequestParam String unityT, @RequestParam String category,RedirectAttributes ra){
        
        Product p = new Product();
        p.setName(name);
        p.setProteins(proteins);
        p.setCarbohydrates(carbohydrates);
        p.setFats(fats);
        p.setDescription(description);
        p.setPrice(price);
        p.setStock(stock);
        p.setGramsPer(gramsPer);
        p.setUnityT(unityT);
        p.setCategory(category);

        repository.save(p);
        ra.addFlashAttribute("success", "true");
        
        return "redirect:/";
    }
        
    @PostMapping("/product/delete")
    public String delete(@RequestParam int deleteId, RedirectAttributes ra) {
        Product p = repository.findById(deleteId).orElse(null);
        if(p!=null){
            p.setActive(false);
            repository.save(p);
            ra.addFlashAttribute("deactiveSuccess","true");
        
        }else{
            ra.addFlashAttribute("deactiveError", "true");
        }
        return "redirect:/";
    }


    @PostMapping("/product/active")
    public String active(@RequestParam int activeId, RedirectAttributes ra) {
        Product p = repository.findById(activeId).orElse(null);
        if(p!=null){
            p.setActive(true);
            repository.save(p);
            ra.addFlashAttribute("reactiveSuccess","true");
        
        }else{
            ra.addFlashAttribute("reactiveError", "true");
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String show(Model model) {
        model.addAttribute("products", repository.findByActiveTrue());
        return "index";
    }
    @GetMapping("/product/find")
    public String find(@RequestParam String findName, Model model) {
        List<Product> results= repository.findByNameContainingIgnoreCaseAndActiveTrue(findName);
        model.addAttribute("results", results);
        model.addAttribute("products", repository.findByActiveTrue());
        return "index";
    }

    @GetMapping("/product/details")
    public String getMethodName(@RequestParam int id, Model model) {
        model.addAttribute("productDetails", repository.findById(id).orElse(null));
        model.addAttribute("products", repository.findByActiveTrue());

        return "index";
    }
    
    
    @GetMapping("/product/updateId")  
    public String showUpdate(@RequestParam (required = false)Integer updateIdP, Model model) {
        if(updateIdP != null){
            Product product = repository.findById(updateIdP).orElse(null);
            model.addAttribute("updateProduct", product);
        }
        model.addAttribute("productos", repository.findByActiveTrue());        
        return "index";
    }
    
    @PostMapping("/product/update")
    public String updateProduct(@RequestParam int id, @RequestParam String updName,
            @RequestParam Double updProteins, @RequestParam Double updCarbohydrates,
            @RequestParam Double updFats, @RequestParam String updDescription,
            @RequestParam Double updPrice, @RequestParam int updStock,
            @RequestParam Double updGramsPer, @RequestParam String updUnityT,
            @RequestParam String updCategory, RedirectAttributes ra) {

        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            product.setName(updName);
            product.setProteins(updProteins);
            product.setCarbohydrates(updCarbohydrates);
            product.setFats(updFats);
            product.setDescription(updDescription);
            product.setPrice(updPrice);
            product.setStock(updStock);
            product.setGramsPer(updGramsPer);
            product.setUnityT(updUnityT);
            product.setCategory(updCategory);
            repository.save(product);
            ra.addFlashAttribute("success", "true");
        } else {
            ra.addFlashAttribute("error", "true");
        }
        return "redirect:/";
    } 
}

