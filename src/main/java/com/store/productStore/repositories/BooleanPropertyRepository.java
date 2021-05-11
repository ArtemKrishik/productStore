package com.store.productStore.repositories;


import org.springframework.data.repository.CrudRepository;
import com.store.productStore.models.BooleanProperty;

import java.util.List;

public interface BooleanPropertyRepository extends CrudRepository<BooleanProperty,Long> {
    List<BooleanProperty> findByProduct_Id(Long productId);
}
