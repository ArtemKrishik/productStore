package com.store.productStore.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Products")
public class Product extends AEntity {

    @Column(name = "ProductName")
    private String name;
    @Column(name = "ProductCost")
    private Double cost;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BooleanProperty> booleanProperties=new ArrayList<BooleanProperty>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List <NumericalProperty> numericalProperties=new ArrayList<NumericalProperty>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<NumericalProperty> getNumericalProperties() {
        return numericalProperties;
    }

    public void setNumericalProperties(List<NumericalProperty> numericalProperties) {
        this.numericalProperties = numericalProperties;
    }


    public List<BooleanProperty> getBooleanProperties() {
        return booleanProperties;
    }

    public void setBooleanProperties(List<BooleanProperty> booleanProperties) {
        this.booleanProperties = booleanProperties;
    }

    public Product(){}

    public Product(String name, List<BooleanProperty>booleanProperties, List<NumericalProperty>numericalProperties, Double cost){
        this.name=name;
        this.booleanProperties=booleanProperties;
        this.numericalProperties=numericalProperties;
        this.cost=cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCost() { return cost; }

    public void addBooleanProperty(BooleanProperty booleanProperty) {
        booleanProperties.add(booleanProperty);
        booleanProperty.setProduct(this);
    }

    public void addNumericalProperty(NumericalProperty numericalProperty) {
        numericalProperties.add(numericalProperty);
        numericalProperty.setProduct(this);
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
        return "Product{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", id=" + id +
                ", booleanProperties=" + booleanProperties +
                ", numericalProperties=" + numericalProperties +
                '}';
    }
}

