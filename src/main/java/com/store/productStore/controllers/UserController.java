package com.store.productStore.controllers;


import com.store.productStore.models.BooleanProperty;
import com.store.productStore.models.NumericalProperty;
import com.store.productStore.models.Product;
import com.store.productStore.models.User;
import com.store.productStore.repositories.BooleanPropertyRepository;
import com.store.productStore.repositories.NumericalPropertiesRepository;
import com.store.productStore.repositories.ProductRepository;
import com.store.productStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BooleanPropertyRepository booleanPropertyRepository;
    @Autowired
    private NumericalPropertiesRepository numericalPropertiesRepository;

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
    public String registrate(Map<String, Object> model, @RequestParam String login, @RequestParam String password, @RequestParam String repeatPassword) {
        User user = userRepository.findByName(login);
        if (user==null) {
            user=new User(login, password);
            userRepository.save(user);
            String str="redirect:/catalog/"+user.getId().toString();
            model.put("user", user);
            // return "logInPage";
            return str;
        } else {

            model.put("message", "user exists");
            return "registrationPage";
        }
    }

    @PostMapping("/logIn")
    public String log(Map<String, Object> model, @RequestParam String login, @RequestParam String password ) {
        User user = userRepository.findByName(login);
        if (user==null) {
            model.put("message", "user doesn`t exists");

            return "logInPage";
        } else {
            if(user.getPassword().equals(password)) {

                String str="redirect:/catalog/"+user.getId().toString();
                model.put("user", user);
               // return "logInPage";
                return str;
            }else
            {
                model.put("message", "password is incorreect");

                return "logInPage";
            }
        }
    }

    @GetMapping("/catalog/{id}")
    public String catalog( Map<String, Object> model, @PathVariable(value = "id") long id) {
        Iterable<Product> products=productRepository.findAll();
        model.put("products", products);

        Optional<User>user=userRepository.findById(id);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);
        model.put("user", users.get(0));

        return "catalog";
    }

    @GetMapping("/productInfoForUser/{id}")
    public String changeInfoAboutProduct(@PathVariable(value = "id") long id, Model model) {

        Optional<Product> product=productRepository.findById(id);
        Iterable<BooleanProperty> booleanProperties= booleanPropertyRepository.findByProduct_Id(id);//ne tot id
        Iterable<NumericalProperty> numericalProperties=numericalPropertiesRepository.findByProduct_Id(id);//ne tot id

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);

        model.addAttribute("booleanProperties", booleanProperties );

        model.addAttribute("numericalProperties", numericalProperties );
        return "infoAboutProductForUser";
    }


    @GetMapping("/buyProduct/{productId}/{userId}")
    public String buyProduct(@PathVariable(value = "productId") long productId, @PathVariable(value = "userId") long userId, Model model) {
        Optional<Product> product=productRepository.findById(productId);
        Optional<User> user=userRepository.findById(userId);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);

        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        users.get(0).addProductToProductCart(prod.get(0));
        userRepository.save(users.get(0));
        String str="redirect:/productCurtPage/"+users.get(0).getId().toString();
        return str;
    }

    @GetMapping("/productCurtPage/{userId}")
    public String pageOfProducts( Model model, @PathVariable(value = "userId") long userId) {
        Optional<User> user=userRepository.findById(userId);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);
        model.addAttribute("products", users.get(0).getProductCart());

        return "productCurtPage";
    }
}
