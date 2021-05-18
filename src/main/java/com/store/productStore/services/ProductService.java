package com.store.productStore.services;

import com.store.productStore.models.BooleanProperty;
import com.store.productStore.models.NumericalProperty;
import com.store.productStore.models.Product;
import com.store.productStore.models.User;
import com.store.productStore.repositories.BooleanPropertyRepository;
import com.store.productStore.repositories.NumericalPropertiesRepository;
import com.store.productStore.repositories.ProductRepository;
import com.store.productStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BooleanPropertyRepository booleanPropertyRepository;
    @Autowired
    private NumericalPropertiesRepository numericalPropertiesRepository;

    public void addProduct(String name, Double price, Integer quantity){
        Product product=new Product(name, price, quantity);
        productRepository.save(product);
    }

    public Iterable<Product> getAllProducts(){
        Iterable<Product> products=productRepository.findAll();
        return products;
    }

    public List<Product> getNonegativeProducts(){
        Iterable<Product> allProducts=productRepository.findAll();
        List<Product> products=new ArrayList<>();
        for(Product p : allProducts)
        {
            if(p.getQuantity()>0)products.add(p);
        }
        return products;
    }

    public void deleteProductById(Long id){
        Optional<Product> product=productRepository.findById(id);
        List<Product> prod=new ArrayList<>();
        product.ifPresent(prod::add);
        List<User> users=prod.get(0).getUsers();
        if(users.size()!=0)
        {
            for(User user : users)
            {
                user.removeProductFromProductCart(prod.get(0));
                userRepository.save(user);
            }
        }
        productRepository.deleteById(id);
    }

    public void addBooleanProperty(Long id, String name, String valueOfProperty){
        Optional<Product> product=productRepository.findById(id);
        List<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        BooleanProperty booleanProperty;
        if(valueOfProperty.equals("true")){
            booleanProperty = new BooleanProperty(name, true, prod.get(0));
        }
        else{
            booleanProperty = new BooleanProperty(name, false, prod.get(0));
        }
        booleanPropertyRepository.save(booleanProperty);
    }

    public void addNumericalProperty(Long id, String name, String valueOfProperty){
        Optional<Product> product=productRepository.findById(id);
        ArrayList<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        NumericalProperty numericalProperty;
        numericalProperty = new NumericalProperty(name, Double.parseDouble(valueOfProperty), prod.get(0));
        numericalPropertiesRepository.save(numericalProperty);
    }

    public List<Product> getProductById(Long id){
        Optional<Product> product=productRepository.findById(id);
        List<Product>prod=new ArrayList<>();
        product.ifPresent(prod::add);
        return prod;
    }

    public Iterable<BooleanProperty> getBooleanPropertiesOfProductByProductId(Long id){
        Iterable<BooleanProperty> booleanProperties=booleanPropertyRepository.findByProduct_Id(id);
        return booleanProperties;
    }

    public Iterable<NumericalProperty> getNumericalPropertiesOfProductByProductId(Long id){
        Iterable<NumericalProperty> numericalProperties=numericalPropertiesRepository.findByProduct_Id(id);
        return numericalProperties;
    }
}
