package com.store.productStore.repositories;

import com.store.productStore.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
