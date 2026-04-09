package com.order.order_service.controllers;

import com.order.order_service.client.pojo.TokenResponse;
import com.order.order_service.exceptions.NoRecordFoundException;
import com.order.order_service.vo.VOOrder;
import com.order.order_service.client.services.AuthService;
import com.order.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String scope) {
        Map<String, Object> response = new HashMap<>();

        if (scope == null || (!scope.equals("user") && !scope.equals("admin"))) {
            response.put("error", "Invalid scope. Must be 'user' or 'admin'");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            TokenResponse tokenResponse = authService.getToken(scope);
            response.put("access_token", tokenResponse.getAccessToken());
            response.put("token_type", tokenResponse.getTokenType());
            response.put("expires_in", tokenResponse.getExpiresIn());
            response.put("scope", tokenResponse.getScope());
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to authenticate with OAuth server");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        List<VOOrder> orderList = orderService.getAllOrders();
        if (orderList.isEmpty()) {
            throw new NoRecordFoundException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK);
        response.put("message", "Orders retrieved successfully");
        response.put("orders", orderList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id) {
        VOOrder order = orderService.getOrderById(id);
        Map<String, Object> response = new HashMap<>();
        if (order == null) {
            response.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("message", "Order retrieved successfully");
        response.put("order", order);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody VOOrder voOrder) {
        Map<String, Object> response = new HashMap<>();
        if (voOrder == null) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }
        VOOrder createdOrder = orderService.createOrder(voOrder);
        response.put("message", "Order created successfully");
        response.put("order", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id, @RequestBody VOOrder voOrder) {
        Map<String, Object> response = new HashMap<>();
        if (voOrder == null) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }
        VOOrder updatedOrder = orderService.updateOrder(id, voOrder);
        if (updatedOrder == null) {
            response.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("message", "Order updated successfully");
        response.put("order", updatedOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = orderService.deleteOrder(id);
        if (!deleted) {
            response.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("message", String.format("Order with id %d deleted successfully", id));
        return ResponseEntity.ok(response);
    }
}
