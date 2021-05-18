package com.store.productStore.controllers;

import com.store.productStore.services.AdministratorService;
import com.store.productStore.services.ProductService;
import com.store.productStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ProductService productService;

    @GetMapping("/pageOfUsers")
    public String pageOfUsers( Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admins", administratorService.getAllAdmins());
        return "pageOfUsers";
    }

    @GetMapping("/addPage")
    public String addPage() {
        return "addPage";
    }

    @PostMapping("/addPage")
    public String add(@RequestParam String name, @RequestParam String password) {
        administratorService.addAdmin(name, password);
        return "redirect:/pageOfUsers";
    }

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteUserById(id);
        return "redirect:/pageOfUsers";
    }

    @GetMapping("/adminDelete/{id}")
    public String deleteAdmin(@PathVariable(value = "id") long id) {
        administratorService.deleteAdminById(id);
        return "redirect:/pageOfUsers";
    }

    @GetMapping("/addProduct")
    public String addProduct() {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String add(@RequestParam String name, @RequestParam Double price, @RequestParam Integer quantity) {
        productService.addProduct(name, price, quantity);
        return "redirect:/pageOfProducts";
    }

    @GetMapping("/pageOfProducts")
    public String pageOfProducts( Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "pageOfProducts";
    }

    @GetMapping("/productDelete/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteProductById(id);
        return "redirect:/pageOfProducts";
    }

    @GetMapping("/addBooleanProperty/{id}")
    public String addBoolean( @PathVariable(value = "id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "addBoolProperty";
    }

    @GetMapping("/addNumericalProperty/{id}")
    public String addNumerical( @PathVariable(value = "id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "addNumProperty";
    }

    @PostMapping("/addNumProperty/{id}")
    public String addNumericalProperty(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String valueOfProperty) {
        productService.addNumericalProperty(id, name, valueOfProperty);
        return "redirect:/productInfo/{id}";
    }

    @PostMapping("/addBoolProperty/{id}")
    public String addBooleanProperty(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String valueOfProperty) {
        productService.addBooleanProperty(id, name, valueOfProperty);
        return "redirect:/productInfo/{id}";
    }

    @GetMapping("/productInfo/{id}")
    public String changeInfoAboutProduct(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("booleanProperties", productService.getBooleanPropertiesOfProductByProductId(id));
        model.addAttribute("numericalProperties", productService.getNumericalPropertiesOfProductByProductId(id));
        return "pageOfProduct";
    }
}
