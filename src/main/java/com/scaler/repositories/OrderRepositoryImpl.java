package com.scaler.repositories;

import com.scaler.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository{
    List<Order> orders = new ArrayList<>();
    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }
}
