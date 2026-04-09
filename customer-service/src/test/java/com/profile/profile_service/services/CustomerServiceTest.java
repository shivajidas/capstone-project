package com.profile.profile_service.services;

import com.profile.profile_service.VO.VOCustomer;
import com.profile.profile_service.entities.Customer;
import com.profile.profile_service.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_returnsCustomerList() {
        Customer customer = Customer.builder().customerId(10L).name("Name1").email("name1@test.com").build();
        when(customerRepo.findAll()).thenReturn(List.of(customer));

        List<VOCustomer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Name1", result.get(0).getName());
    }

    @Test
    void getCustomerById_found() {
        Customer customer = Customer.builder().customerId(10L).name("Name2").email("name2@test.com").build();
        when(customerRepo.findById(10L)).thenReturn(Optional.of(customer));

        VOCustomer result = customerService.getCustomerById(10L);

        assertNotNull(result);
        assertEquals("Name2", result.getName());
    }

    @Test
    void getCustomerById_notFound() {
        when(customerRepo.findById(99L)).thenReturn(Optional.empty());

        VOCustomer result = customerService.getCustomerById(99L);

        assertNull(result);
    }

    @Test
    void addCustomer_success() {
        Customer saved = Customer.builder().customerId(10L).name("Name3").email("name3@test.com").zipcode(12345).build();
        when(customerRepo.save(any(Customer.class))).thenReturn(saved);

        VOCustomer input = VOCustomer.builder().name("Name3").email("name3@test.com").zipcode(12345).build();
        VOCustomer result = customerService.addCustomer(input);

        assertNotNull(result);
        assertEquals(10L, result.getCustomerId());
    }

    @Test
    void deleteCustomerById_exists() {
        when(customerRepo.existsById(10L)).thenReturn(true);

        boolean result = customerService.deleteCustomerById(10L);

        assertTrue(result);
        verify(customerRepo).deleteById(10L);
    }

    @Test
    void deleteCustomerById_notExists() {
        when(customerRepo.existsById(99L)).thenReturn(false);

        boolean result = customerService.deleteCustomerById(99L);

        assertFalse(result);
    }
}
