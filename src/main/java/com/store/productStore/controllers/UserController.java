package com.store.productStore.controllers;

import com.store.productStore.models.*;
import com.store.productStore.services.AdministratorService;
import com.store.productStore.services.ProductService;
import com.store.productStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ProductService productService;

    @GetMapping("/homePage")
    public String homePage( Model model) {
        model.addAttribute(model);
        return "homePage";
    }

    @GetMapping("/logIn")
    public String logIn( Model model) {
        model.addAttribute(model);
        return "logInPage";
    }

    @GetMapping("/registration")
    public String registration( Model model) {
        model.addAttribute(model);
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String registration(Map<String, Object> model, @RequestParam String login, @RequestParam String password, @RequestParam String repeatPassword) {
        User user = userService.getUserByName(login);
        Administrator admin = administratorService.getAdministratorByName(login);
        if (user==null && admin==null) {
            user=new User(login, password);
            userService.addUser(user);
            String str="redirect:/catalog/"+user.getId().toString();
            model.put("user", user);
            return str;
        } else {
            model.put("message", "user exists");
            return "registrationPage";
        }
    }

    @PostMapping("/logIn")
    public String log(Map<String, Object> model, @RequestParam String login, @RequestParam String password ) {

        User user = userService.getUserByName(login);
        Administrator admin = administratorService.getAdministratorByName(login);
        if (user==null && admin==null)
        {
            model.put("message", "user doesn't exists");
            return "logInPage";
        }
        else if (admin!=null && admin.getPassword().equals(password))
        {
            return "redirect:/pageOfProducts";
        }
        else if (user!=null && user.getPassword().equals(password))
        {
            String str="redirect:/catalog/"+user.getId().toString();
            model.put("user", user);
            return str;
        }
        else
        {
            model.put("message", "password is incorrect");
            return "logInPage";
        }
    }

    @GetMapping("/catalog/{id}")
    public String catalog(Map<String, Object> model, @PathVariable(value = "id") long id) {
        model.put("products", productService.getNonegativeProducts());
        model.put("user", userService.getUserById(id).get(0));
        return "catalog";
    }

    @GetMapping("/productInfoForUser/{productId}/{userId}")
    public String infoAboutProduct(@PathVariable(value = "productId") long id, @PathVariable(value = "userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("booleanProperties", productService.getBooleanPropertiesOfProductByProductId(id));
        model.addAttribute("numericalProperties", productService.getNumericalPropertiesOfProductByProductId(id));
        return "infoAboutProductForUser";
    }

    @PostMapping("/productInfoForUser/{productId}/{userId}")
    public String buyProductProduct(@PathVariable(value = "productId") long productId, @PathVariable(value = "userId") long userId, @RequestParam Integer quantity) {
        userService.addProductToUsersProductCart(userId, productId, quantity);

        return "redirect:/productCurtPage/"+userService.getUserById(userId).get(0).getId().toString();
    }

    @GetMapping("/buyProduct/{productId}/{userId}")
    public String buy1Product(@PathVariable(value = "productId") long productId, @PathVariable(value = "userId") long userId,Map<String, Object> model) {
        if(userService.addProductToUsersProductCart(userId, productId, 1))
        {

        return "redirect:/productCurtPage/"+userService.getUserById(userId).get(0).getId().toString();
        }
        else
        {
            return "redirect:/catalog/"+userService.getUserById(userId).get(0).getId().toString();
        }
    }

    @GetMapping("/productCurtPage/{userId}")
    public String productCartPage( Model model, @PathVariable(value = "userId") long userId) {
        model.addAttribute("products", userService.getUserById(userId).get(0).getProductCart());
        model.addAttribute("cost", userService.getUserById(userId).get(0).getCostOfProductsInProductCart());
        return "productCurtPage";
    }

}
