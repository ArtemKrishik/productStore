package com.store.productStore.repositories;

import com.store.productStore.models.BooleanProperty;
import com.store.productStore.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface BooleanPropertiesRepository extends CrudRepository<BooleanProperty, Long> {
}
