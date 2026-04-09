package com.order.order_service.repositories;

import com.order.order_service.entities.OrderTable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderTable, Long> {
}
