package com.store.productStore.repositories;

import com.store.productStore.models.Administrator;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Administrator,Long> {
    Administrator findByName(String name);
}
