package com.store.productStore.controllers;


import com.store.productStore.models.*;
import com.store.productStore.repositories.*;
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
    private BooleanPropertyRepository booleanPropertiesRepository;
    @Autowired
    private NumericalPropertiesRepository numericalPropertiesRepository;
    @Autowired
    private AdminRepository adminRepository;

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
        Administrator admin = adminRepository.findByName(login);

        if (user==null && admin==null) {
            user=new User(login, password);
            userRepository.save(user);
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
        User user = userRepository.findByName(login);
        Administrator admin = adminRepository.findByName(login);
        if (admin==null && user==null)
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
        Iterable<BooleanProperty> booleanProperties=booleanPropertiesRepository.findByProduct_Id(id);//ne tot id
        Iterable<NumericalProperty> numericalProperties=numericalPropertiesRepository.findByProduct_Id(id);//ne tot i
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);

        model.addAttribute("booleanProperties", booleanProperties );

        model.addAttribute("numericalProperties", numericalProperties );
        return "infoAboutProductForUser";
    }

    @GetMapping("/buyProduct/{productId}/{userId}")
    public String buy1Product(@PathVariable(value = "productId") long productId, @PathVariable(value = "userId") long userId, Model model) {
        Optional<Product> product=productRepository.findById(productId);
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        prod.get(0).setQuantity(prod.get(0).getQuantity()-1);

        Optional<User> user=userRepository.findById(userId);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);

        if(!prod.get(0).getUsers().contains(users.get(0))){
        prod.get(0).addUser(users.get(0));
        productRepository.save(prod.get(0));}

        users.get(0).addProductToProductCart(prod.get(0));
        userRepository.save(users.get(0));
        return "redirect:/productCurtPage/"+users.get(0).getId().toString();
    }

    @GetMapping("/productCurtPage/{userId}")
    public String pageOfProducts( Model model, @PathVariable(value = "userId") long userId) {
        Optional<User> user=userRepository.findById(userId);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);
        model.addAttribute("products", users.get(0).getProductCart());
        model.addAttribute("cost", users.get(0).getCostOfProductsInProductCart());
        return "productCurtPage";
    }
}
