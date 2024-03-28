package com.melek.springcloudcontractmanager.order;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }


    public List<Order> getUserOrders(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }


}
