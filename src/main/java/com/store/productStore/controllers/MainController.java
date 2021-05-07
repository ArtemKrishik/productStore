package com.store.productStore.controllers;



import com.store.productStore.models.BooleanProperty;
import com.store.productStore.models.NumericalProperty;
import com.store.productStore.models.Product;
import com.store.productStore.models.User;
import com.store.productStore.repositories.BooleanPropertiesRepository;
import com.store.productStore.repositories.NumericalPropertiesRepository;
import com.store.productStore.repositories.ProductRepository;
import com.store.productStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


@Controller
public class MainController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BooleanPropertiesRepository booleanPropertiesRepository;
    @Autowired
    private NumericalPropertiesRepository numericalPropertiesRepository;

    @GetMapping("/pageOfUsers")
    public String greeting( Model model) {
        Iterable<User> users=userRepository.findAll();
        model.addAttribute("users", users);
        return "pageOfUsers";
    }

    @GetMapping("/addPage")
    public String addPage( Model model) {

        return "addPage";
    }


    @PostMapping("/addPage")
    public String add(@RequestParam String name, @RequestParam String password, Model model) {
        User user=new User(name, password);
        userRepository.save(user);

        return "redirect:/pageOfUsers";
    }

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        userRepository.deleteById(id);

        return "redirect:/pageOfUsers";
    }

    @GetMapping("/addProduct")
    public String addProduct( Model model) {

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String add(@RequestParam String name, @RequestParam Double price, Model model) {
        Product product=new Product(name, price);
        productRepository.save(product);

        return "redirect:/pageOfProducts";
    }

    @GetMapping("/pageOfProducts")
    public String pageOfProducts( Model model) {
        Iterable<Product> products=productRepository.findAll();
        model.addAttribute("products", products);
        return "pageOfProducts";
    }

    @GetMapping("/productDelete/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id, Model model) {
        productRepository.deleteById(id);

        return "redirect:/pageOfProducts";
    }



    @GetMapping("/productInfo/{id}")
    public String changeInfoAboutProduct(@PathVariable(value = "id") long id, Model model) {
        productRepository.findById(id);
        Optional<Product> product=productRepository.findById(id);
        //Iterable<BooleanProperty> booleanProperties=booleanPropertiesRepository.findAllById(Collections.singleton(id));//ne tot id
        //Iterable<NumericalProperty> numericalProperties=numericalPropertiesRepository.findAllById(Collections.singleton(id));//ne tot id
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);

        //model.addAttribute("booleanProperties", booleanProperties );

        //model.addAttribute("numericalProperties", numericalProperties );
        return "pageOfProduct";
    }
}
