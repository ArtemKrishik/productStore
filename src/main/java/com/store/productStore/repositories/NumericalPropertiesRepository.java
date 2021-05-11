package com.store.productStore.repositories;


import com.store.productStore.models.NumericalProperty;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NumericalPropertiesRepository extends CrudRepository<NumericalProperty, Long> {
    List<NumericalProperty> findByProduct_Id(Long productId);

}
