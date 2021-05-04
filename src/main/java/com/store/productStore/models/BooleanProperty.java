package com.store.productStore.models;

import javax.persistence.*;

@Entity
@Table(name = "BooleanProperties")
public class BooleanProperty extends Property {
    @Column(name = "PropertyValueBool")
    private Boolean propertyValue;
    @Column(name = "PropertyName")
    private String propertyName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;


    public BooleanProperty(){}
    public BooleanProperty(String propertyName, Boolean propertyValue) {
        this.propertyName=propertyName;
        this.propertyValue=propertyValue;
    }

    @Override
    public String toString() {
        return "BooleanProperty{" +
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

    public Boolean getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Boolean propertyValue) {
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
