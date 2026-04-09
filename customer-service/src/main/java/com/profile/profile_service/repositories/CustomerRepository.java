package com.profile.profile_service.repositories;

import com.profile.profile_service.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
