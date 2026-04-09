package com.profile.profile_service.controllers;

import com.profile.profile_service.Exceptions.NoRecordFoundException;
import com.profile.profile_service.VO.VOCustomer;
import com.profile.profile_service.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CrudOperationController {

    @Autowired
    public CustomerService customerService;

    @GetMapping("/api/customer/getAllCustomers")
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        List<VOCustomer> customerList = customerService.getAllCustomers();
        if (customerList.isEmpty()) {
            throw new NoRecordFoundException();
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Status", HttpStatus.OK);
        responseMap.put("Message", "Extracted customer list");
        responseMap.put("CustomerList", customerList);

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @GetMapping("/api/customer/getCustomerById/{id}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable Long id) {
        VOCustomer customerById = customerService.getCustomerById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (null == customerById) {
            responseMap.put("message", "Failed to find customer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", "Extracted customer");
        responseMap.put("customer", customerById);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @DeleteMapping("/api/customer/delete-by-id/{id}")
    public ResponseEntity<Map<String, Object>> deleteCustomerById(@PathVariable Long id) {
        boolean customerDeletedSuccessfully = customerService.deleteCustomerById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (!customerDeletedSuccessfully) {
            responseMap.put("message", "Failed to delete customer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", String.format("Customer with id %d deleted successfully", id));
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @PostMapping("/api/customer/add-customer")
    public ResponseEntity<Map<String, Object>> addCustomer(@RequestBody VOCustomer customer) {
        Map<String, Object> responseMap = new HashMap<>();
        if (null == customer) {
            responseMap.put("message", "Please check message body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        VOCustomer customerAdded = customerService.addCustomer(customer);
        responseMap.put("message", "Customer Added Successfully");
        responseMap.put("Customer", customerAdded);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @PutMapping("/api/customer/update-customer/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomer(@PathVariable Long id, @RequestBody VOCustomer customer) {
        Map<String, Object> responseMap = new HashMap<>();
        if (null == customer) {
            responseMap.put("message", "Please check message body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        VOCustomer customerUpdated = customerService.updateCustomerById(id, customer);
        if (null == customerUpdated) {
            responseMap.put("message", "Failed to update customer - customer not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", "Customer Updated Successfully");
        responseMap.put("Customer", customerUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
