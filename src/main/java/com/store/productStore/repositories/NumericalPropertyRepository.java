package com.store.productStore.repositories;

import com.store.productStore.models.NumericalProperty;
import org.springframework.data.repository.CrudRepository;

public interface NumericalPropertyRepository extends CrudRepository<NumericalProperty, Long> {
}
