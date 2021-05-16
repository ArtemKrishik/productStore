package com.store.productStore.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User extends AEntity {

    @Column(name = "UserName")
    private String name;
    @Column(name = "UserPassword")
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;


    @ManyToMany( fetch = FetchType.EAGER)
    private List<Product> productCart;



    public User() {
        productCart=new ArrayList<>();
    }


    public User(String name, String password) {
        this.name = name;
        this.password = password;

    }


    public Double getCostOfProductsInProductCart(){
        Double cost=0.0;
        for(Product p : productCart){cost+=p.getCost();}
        return cost;
    }

    public void removeProductFromProductCart(Product product) {

        productCart.remove(product);
    }

    public void addProductToProductCart(Product product) {

        productCart.add(product);
    }

    public List<Product> getProductCart() {
        return productCart;
    }

    public void setProductCart(List<Product> productCart) {
        this.productCart = productCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", productCart=" + productCart +
                '}';
    }
}
