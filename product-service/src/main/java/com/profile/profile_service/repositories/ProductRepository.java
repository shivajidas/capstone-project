package com.profile.profile_service.repositories;

import com.profile.profile_service.data.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
