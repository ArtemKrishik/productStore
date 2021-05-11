package com.store.productStore.models;

import javax.persistence.*;


@Entity
@Table(name = "Admins")
public class Administrator extends AEntity {

    @Column(name = "AdminName")
    private String name;
    @Column(name = "AdminPassword")
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    public Administrator() {
    }

    public Administrator(String name, String password) {
        this.name = name;
        this.password = password;

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
        return "Admin{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id;
    }
}
