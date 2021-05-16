package com.store.productStore.controllers;



import com.store.productStore.models.*;
import com.store.productStore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BooleanPropertyRepository booleanPropertyRepository;
    @Autowired
    private NumericalPropertiesRepository numericalPropertiesRepository;


    @GetMapping("/pageOfUsers")
    public String pageOfUsers( Model model) {
        Iterable<User> users=userRepository.findAll();
        model.addAttribute("users", users);
        Iterable<Administrator> admins=adminRepository.findAll();
        model.addAttribute("admins", admins);
        return "pageOfUsers";
    }

    @GetMapping("/addPage")
    public String addPage( Model model) {

        return "addPage";
    }


    @PostMapping("/addPage")
    public String add(@RequestParam String name, @RequestParam String password, Model model) {
        Administrator admin=new Administrator(name, password);
        adminRepository.save(admin);

        return "redirect:/pageOfUsers";
    }

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        userRepository.deleteById(id);

        return "redirect:/pageOfUsers";
    }

    @GetMapping("/adminDelete/{id}")
    public String deleteAdmin(@PathVariable(value = "id") long id, Model model) {
        adminRepository.deleteById(id);

        return "redirect:/pageOfUsers";
    }

    @GetMapping("/addProduct")
    public String addProduct( Model model) {

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String add(@RequestParam String name, @RequestParam Double price, @RequestParam Integer quantity, Model model) {
        Product product=new Product(name, price, quantity);
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
    public String deleteProduct(@PathVariable(value = "id") long id) {
        Optional<Product> product=productRepository.findById(id);
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        List<User> users=prod.get(0).getUsers();
        if(users.size()!=0)
        {
            for(User user : users){
                user.removeProductFromProductCart(prod.get(0));
                userRepository.save(user);
            }

        }
        productRepository.delete(prod.get(0));


        return "redirect:/pageOfProducts";
    }



    @PostMapping("/addBoolProperty/{id}")
    public String addBooleanProperty(@PathVariable(value = "id") long id, Model model, @RequestParam String name, @RequestParam String valueOfProperty) {

        Optional<Product> product=productRepository.findById(id);

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        BooleanProperty booleanProperty;
        if(valueOfProperty.equals("true")){
            booleanProperty = new BooleanProperty(name, true, prod.get(0));
        }
        else{
            booleanProperty = new BooleanProperty(name, false, prod.get(0));
        }
        booleanPropertyRepository.save(booleanProperty);

        return "redirect:/productInfo/{id}";
    }

    @GetMapping("/addBooleanProperty/{id}")
    public String addBoolean( @PathVariable(value = "id") long id, Model model) {

        Optional<Product> product=productRepository.findById(id);

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);
        return "addBoolProperty";
    }
    @PostMapping("/addNumProperty/{id}")
    public String addNumericalProperty(@PathVariable(value = "id") long id, Model model, @RequestParam String name, @RequestParam String valueOfProperty) {

        Optional<Product> product=productRepository.findById(id);

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        NumericalProperty numericalProperty;

        numericalProperty = new NumericalProperty(name, Double.parseDouble(valueOfProperty), prod.get(0));

        numericalPropertiesRepository.save(numericalProperty);

        return "redirect:/productInfo/{id}";
    }

    @GetMapping("/addNumericalProperty/{id}")
    public String addNumerical( @PathVariable(value = "id") long id, Model model) {

        Optional<Product> product=productRepository.findById(id);

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);
        return "addNumProperty";
    }



    @GetMapping("/productInfo/{id}")
    public String changeInfoAboutProduct(@PathVariable(value = "id") long id, Model model) {

        Optional<Product> product=productRepository.findById(id);
        Iterable<BooleanProperty> booleanProperties=booleanPropertyRepository.findByProduct_Id(id);//ne tot id
        Iterable<NumericalProperty> numericalProperties=numericalPropertiesRepository.findByProduct_Id(id);//ne tot id
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);

        model.addAttribute("booleanProperties", booleanProperties );

        model.addAttribute("numericalProperties", numericalProperties );
        return "pageOfProduct";
    }




}
