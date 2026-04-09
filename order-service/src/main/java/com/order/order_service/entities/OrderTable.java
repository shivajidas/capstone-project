package com.order.order_service.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column
    private Date date;
    @Column
    private double totalValue;

    @ManyToOne(cascade = CascadeType.MERGE) //Many order to one customer
    @JoinColumn(name = "customerId", nullable = false) // Join column is defined here because this is the owning side since orders cannot exist without customer.
    private Customer customer;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Product> products;


}
