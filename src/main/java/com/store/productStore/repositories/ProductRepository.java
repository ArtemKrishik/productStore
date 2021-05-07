package com.store.productStore.repositories;

import com.store.productStore.models.Product;
import com.store.productStore.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}