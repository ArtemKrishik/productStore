package com.store.productStore.services;

import com.store.productStore.models.Product;
import com.store.productStore.models.User;
import com.store.productStore.repositories.ProductRepository;
import com.store.productStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Iterable<User> getAllUsers(){
         Iterable<User> users=userRepository.findAll();
        return users;
    }

    public void deleteUserById(Long id){
        Optional<User> user=userRepository.findById(id);
        List<User>users=new ArrayList<>();
        user.ifPresent(users::add);
        List<Product> productCart=users.get(0).getProductCart();
        if(productCart.size()!=0){
            for(Product pr : productCart)
            {
                pr.removeUsersFromProduct(users.get(0));
                productRepository.save(pr);
            }
        }
        userRepository.deleteById(id);
    }

    public List<User> getUserById(Long id){
        Optional<User> user=userRepository.findById(id);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);
        return users;
    }

    public User getUserByName(String name){
        return userRepository.findByName(name);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public boolean addProductToUsersProductCart(Long userId, Long productId, Integer quantity){

        Optional<Product> product=productRepository.findById(productId);
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);

        if(prod.get(0).getQuantity()<quantity){return false;}

        prod.get(0).setQuantity(prod.get(0).getQuantity()-quantity);

        Optional<User> user=userRepository.findById(userId);
        ArrayList<User>users=new ArrayList<>();
        user.ifPresent(users::add);

        if(!prod.get(0).getUsers().contains(users.get(0)))
        {
            prod.get(0).addUser(users.get(0));
            productRepository.save(prod.get(0));
        }
        for(int i=0; i<quantity; i++)
        {
            users.get(0).addProductToProductCart(prod.get(0));
        }
        userRepository.save(users.get(0));
        return true;

    }
}