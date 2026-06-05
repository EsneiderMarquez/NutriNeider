
package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    
    //metodo post para el insertar a la base de datos
    @PostMapping("/product/insert")
    public String insert(@RequestParam String name, @RequestParam Double proteins, @RequestParam Double carbohydrates
        , @RequestParam Double fats, @RequestParam String description, @RequestParam Double price, @RequestParam int stock
        , @RequestParam Double gramsPer, @RequestParam String unityT, @RequestParam String category,RedirectAttributes ra){
        
        //se crea un producto y se le cambian todos sus atributos por los parametros
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

        try{
            //lo guarda en la base de datos con el repository
            repository.save(p);
            ra.addFlashAttribute("createSuccess", "true");
        }catch (DataAccessException d){
            ra.addFlashAttribute("createError", "true");
        }
        
        return "redirect:/";
    }
        
    //metodo post para desactivar los productos
    @PostMapping("/product/delete")
    public String delete(@RequestParam int deleteId, RedirectAttributes ra) {
        //busca el producto por la id que se recibe del index y lo assigna a una variable p
        Product p = repository.findById(deleteId).orElse(null);
        if(p!=null){
            //cambia el estado y guarda en la base de datos otra vez
            p.setActive(false);
            repository.save(p);
            ra.addFlashAttribute("deactiveSuccess","true");
        
        }else{
            ra.addFlashAttribute("deactiveError", "true");
        }
        return "redirect:/";
    }

    //metodo post para reactivar los productos
    @PostMapping("/product/active")
    public String active(@RequestParam int activeId, RedirectAttributes ra) {
        //una vez mas busca el producto por la id recibida y lo asigna a una variable p
        Product p = repository.findById(activeId).orElse(null);
        if(p!=null){
            //cambia el estado y lo guarda en la base de datos
            p.setActive(true);
            repository.save(p);
            ra.addFlashAttribute("reactiveSuccess","true");
        
        }else{
            ra.addFlashAttribute("reactiveError", "true");
        }
        return "redirect:/";
    }

    //metodo get para poder mostrar los productos en el fronted SIEMPRE, solo se necesita abir la web, osea "/".
    @GetMapping("/")
    // se usa un parametro model creado por spring boot para regresar informacion al frontend.
    public String show(Model model) {
        model.addAttribute("products", repository.findByActiveTrue());
        return "index";
    }
    // metodo get para poder encontrar un producto por nombre en la base de datos.
    @GetMapping("/product/find")
    public String find(@RequestParam String findName, Model model) {
        //asigna los productos a una lista results para enviarla mediante model a el index
        List<Product> results= repository.findByNameContainingIgnoreCaseAndActiveTrue(findName);
        model.addAttribute("results", results);
        model.addAttribute("products", repository.findByActiveTrue());
        return "index";
    }

    //metodo get para mostrar todos los atributos de los productos.
    @GetMapping("/product/details")
    //manda al frontend la lista de productos y los detalles del producto del cual se selecciono para mostrar mas informacion
    public String getMethodName(@RequestParam int id, Model model) {
        model.addAttribute("productDetails", repository.findById(id).orElse(null));
        model.addAttribute("products", repository.findByActiveTrue());

        return "index";
    }
    
    //metodo get 
    @GetMapping("/product/updateId")  
    public String showUpdate(@RequestParam (required = false)Integer updateIdP, Model model) {
        try{
            if(updateIdP != null){
                Product product = repository.findById(updateIdP).orElse(null);
                model.addAttribute("updateProduct", product);
            }
            model.addAttribute("products", repository.findByActiveTrue());
        }catch (DataAccessException e){
            model.addAttribute("updateIdError", "Error loading product");
        }
        return "index";
    }
    
    //metodo post para actualizar la informacion de un producto
    @PostMapping("/product/update")
    public String updateProduct(@RequestParam int id, @RequestParam String updName,
            @RequestParam Double updProteins, @RequestParam Double updCarbohydrates,
            @RequestParam Double updFats, @RequestParam String updDescription,
            @RequestParam Double updPrice, @RequestParam int updStock,
            @RequestParam Double updGramsPer, @RequestParam String updUnityT,
            @RequestParam String updCategory, RedirectAttributes ra) {

        //Asiga a una variable product el producto encontrado por el repositorio con la id recibida.
        Product product = repository.findById(id).orElse(null);
        //Si el producto si se encontro se acutaliza, de lo contrario retorna el atributo de error
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
            ra.addFlashAttribute("updSuccess", "true");
        } else {
            ra.addFlashAttribute("updError", "true");
        }
        return "redirect:/";
    } 
}

