package com.store.productStore.models;

import javax.persistence.*;

@Entity
@Table(name = "NumericalProperties")
public class NumericalProperty extends Property {
    @Column(name = "PropertyValueDouble")
    private Double propertyValue;
    @Column(name = "PropertyName")
    private String propertyName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;


    public NumericalProperty(){}
    public NumericalProperty(String propertyName, Double propertyValue) {
        this.propertyName=propertyName;
        this.propertyValue=propertyValue;
    }

    @Override
    public String toString() {
        return "DoubleProperty{" +
                "propertyValue=" + propertyValue +
                ", propertyName='" + propertyName + '\'' +
                ", id=" + id +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Double propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}