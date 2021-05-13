package com.store.productStore.repositories;

import com.store.productStore.models.BooleanProperty;
import com.store.productStore.models.NumericalProperty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BooleanPropertyRepository extends CrudRepository<BooleanProperty,Long> {
    List<BooleanProperty> findByProduct_Id(Long productId);
}
