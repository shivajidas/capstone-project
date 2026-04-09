package com.order.order_service.services;

import com.order.order_service.client.pojo.CustomerPojo;
import com.order.order_service.client.services.CustomerClientService;
import com.order.order_service.client.services.ProductClientService;
import com.order.order_service.entities.OrderTable;
import com.order.order_service.exceptions.ApplicationUnavailableException;
import com.order.order_service.vo.ProductInfo;
import com.order.order_service.vo.VOOrder;
import com.order.order_service.entities.Customer;
import com.order.order_service.entities.Product;
import com.order.order_service.repositories.CustomerRepository;
import com.order.order_service.repositories.OrderRepository;
import com.order.order_service.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    CustomerClientService customerClientService;
    @Autowired
    ProductClientService productClientService;

    public List<VOOrder> getAllOrders() {
        Iterable<OrderTable> orders = orderRepository.findAll();
        
        return StreamSupport.stream(orders.spliterator(), false)
                .map(this::mapOrderTableToVO)
                .toList();
    }

    public VOOrder getOrderById(Long id) {
        Optional<OrderTable> orderById = orderRepository.findById(id);
        return orderById.map(this::mapOrderTableToVO).orElse(null);
    }

    public VOOrder createOrder(VOOrder voOrder) {
        log.info("Creating order for customerId: {}, productIds: {}", 
                voOrder.getCustomerId(), voOrder.getProductIds());
        
        Customer customer = null;
        List<Product> products = new ArrayList<>();
        
        // Fetch customer from API and merge into persistence context
        if (voOrder.getCustomerId() != null) {
            try {
                CustomerPojo customerPojo = customerClientService.getCustomerById(voOrder.getCustomerId());
                if (customerPojo != null) {
                    customer = mapCustomerPOJOToEntity(customerPojo);
                    customer = customerRepository.save(customer);
                }
            } catch (Exception e) {
                log.error("Error fetching customer with id {}: {}", voOrder.getCustomerId(), e.getMessage());
                throw new ApplicationUnavailableException("Customer service is unavailable. Please try again later.", null);
            }
        }
        
        // Fetch products from API and merge into persistence context
        if (voOrder.getProductIds() != null && !voOrder.getProductIds().isEmpty()) {
                List<Product> productsFromApi = productClientService.getAllProductsById(voOrder.getProductIds())
                        .stream().map(productPojo -> Product.builder()
                                .productId(productPojo.getProductId())
                                .price(productPojo.getPrice())
                                .name(productPojo.getName())
                                .description(productPojo.getDescription())
                                .quantityAvailable(productPojo.getQuantityAvailable()).build()).toList();
                products = StreamSupport.stream(productRepository.saveAll(productsFromApi).spliterator(), false).toList();
        }

        List<ProductInfo> productInfoList = products.stream().map(
                product -> ProductInfo.builder()
                        .productName(product.getName())
                        .productId(product.getProductId())
                        .productPrice(product.getPrice() != null ? product.getPrice() : 0.0).build()
        ).toList();
        
        double totalValue = productInfoList.stream()
                .mapToDouble(ProductInfo::getProductPrice)
                .sum();
        
        OrderTable orderTable = OrderTable.builder()
                .date(voOrder.getDate() != null ? voOrder.getDate() : new Date())
                .customer(customer)
                .products(products)
                .totalValue(totalValue)
                .build();

        try {
            OrderTable savedOrderTable = orderRepository.save(orderTable);
            log.info("Order created successfully with id: {}", savedOrderTable.getOrderId());
            return mapOrderTableToVO(savedOrderTable, productInfoList);
        } catch (Exception e) {
            log.error("Error saving order to database: {}", e.getMessage());
            throw new RuntimeException("Unable to create order: Database error", e);
        }
    }

    public VOOrder updateOrder(Long id, VOOrder voOrder) {
        Optional<OrderTable> existingOrder = orderRepository.findById(id);

        if (existingOrder.isPresent()) {
            OrderTable orderTable = existingOrder.get();

            if (voOrder.getDate() != null) {
                orderTable.setDate(voOrder.getDate());
            }
            if (voOrder.getCustomerId() != null) {
                Customer customer = customerRepository.findById(voOrder.getCustomerId()).orElse(null);
                orderTable.setCustomer(customer);
            }
            List<ProductInfo> productInfoList = new ArrayList<>();
            if (voOrder.getProductIds() != null && !voOrder.getProductIds().isEmpty()) {
                List<Product> products = StreamSupport.stream(productRepository.findAllById(voOrder.getProductIds()).spliterator(), false)
                        .toList();
                productInfoList = products.stream().map(
                        product -> ProductInfo.builder()
                                .productName(product.getName())
                                .productId(product.getProductId())
                                .productPrice(product.getPrice()).build()
                ).toList();
                orderTable.setProducts(products);
            }

            OrderTable updatedOrderTable = orderRepository.save(orderTable);
            return mapOrderTableToVO(updatedOrderTable, productInfoList);
        }
        return null;
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private VOOrder mapOrderTableToVO(OrderTable orderTable, List<ProductInfo> productInfoList) {
        List<Long> productIds = orderTable.getProducts() != null
                ? orderTable.getProducts().stream().map(Product::getProductId).toList()
                : new ArrayList<>();

        return VOOrder.builder()
                .id(orderTable.getOrderId())
                .date(orderTable.getDate())
                .customerId(orderTable.getCustomer() != null ? orderTable.getCustomer().getCustomerId() : null)
                .productIds(productIds)
                .productInfoList(productInfoList)
                .orderValue(orderTable.getTotalValue())
                .build();
    }

    private VOOrder mapOrderTableToVO(OrderTable orderTable) {
        List<ProductInfo> productInfoList = orderTable.getProducts() != null
                ? orderTable.getProducts().stream().map(product -> ProductInfo.builder()
                        .productId(product.getProductId())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .build()).toList()
                : new ArrayList<>();
        return mapOrderTableToVO(orderTable, productInfoList);
    }

    private Customer mapCustomerPOJOToEntity(CustomerPojo cust){
        return Customer.builder()
                .customerId(cust.getCustomerId())
                .name(cust.getName())
                .email(cust.getEmail())
                .build();
    }
}
