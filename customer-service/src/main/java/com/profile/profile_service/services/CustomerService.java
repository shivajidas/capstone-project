package com.profile.profile_service.services;

import com.profile.profile_service.VO.VOCustomer;
import com.profile.profile_service.entities.Customer;
import com.profile.profile_service.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;

    public List<VOCustomer> getAllCustomers() {
        Iterable<Customer> customers = customerRepo.findAll();
        return StreamSupport.stream(customers.spliterator(), false)
                .map(customer -> VOCustomer.builder()
                        .customerId(customer.getCustomerId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .zipcode(customer.getZipcode())
                        .build())
                .collect(Collectors.toList());
    }

    public VOCustomer getCustomerById(Long id) {
        Optional<Customer> customerById = customerRepo.findById(id);
        return customerById.map(customer -> VOCustomer.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .email(customer.getEmail())
                .zipcode(customer.getZipcode())
                .build()).orElse(null);
    }

    public boolean deleteCustomerById(Long id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public VOCustomer addCustomer(VOCustomer customer) {
        Customer customerAdded = customerRepo.save(Customer.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .zipcode(customer.getZipcode())
                .build());
        return VOCustomer.builder()
                .customerId(customerAdded.getCustomerId())
                .name(customerAdded.getName())
                .email(customerAdded.getEmail())
                .zipcode(customerAdded.getZipcode())
                .build();
    }

    public VOCustomer updateCustomerById(Long id, VOCustomer updatedVOCustomer) {
        Optional<Customer> customerAfterUpdate = customerRepo.findById(id).map(customer -> {
            customer.setName(updatedVOCustomer.getName());
            customer.setEmail(updatedVOCustomer.getEmail());
            customer.setZipcode(updatedVOCustomer.getZipcode());
            return customerRepo.save(customer);
        });
        return customerAfterUpdate.map(customer -> VOCustomer.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .email(customer.getEmail())
                .zipcode(customer.getZipcode())
                .build()).orElse(null);
    }
}
