package com.order.order_service.services;

import com.order.order_service.entities.Customer;
import com.order.order_service.entities.OrderTable;
import com.order.order_service.entities.Product;
import com.order.order_service.repositories.OrderRepository;
import com.order.order_service.vo.VOOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_returnsOrderList() {
        Customer customer = Customer.builder().customerId(1L).name("Name1").build();
        Product product = Product.builder().productId(1L).name("Guitar").price(299.99).build();
        OrderTable order = OrderTable.builder().orderId(1L).date(new Date()).customer(customer).products(List.of(product)).totalValue(299.99).build();
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<VOOrder> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getOrderById_found() {
        Customer customer = Customer.builder().customerId(1L).name("Name2").build();
        Product product = Product.builder().productId(1L).name("Piano").price(899.99).build();
        OrderTable order = OrderTable.builder().orderId(1L).date(new Date()).customer(customer).products(List.of(product)).totalValue(899.99).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        VOOrder result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getOrderById_notFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        VOOrder result = orderService.getOrderById(99L);

        assertNull(result);
    }

    @Test
    void deleteOrder_exists() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        boolean result = orderService.deleteOrder(1L);

        assertTrue(result);
        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrder_notExists() {
        when(orderRepository.existsById(99L)).thenReturn(false);

        boolean result = orderService.deleteOrder(99L);

        assertFalse(result);
    }
}
