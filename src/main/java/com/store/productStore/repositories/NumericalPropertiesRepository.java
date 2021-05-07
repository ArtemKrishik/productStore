package com.store.productStore.repositories;

import com.store.productStore.models.NumericalProperty;
import com.store.productStore.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface NumericalPropertiesRepository extends CrudRepository<NumericalProperty, Long> {
}
