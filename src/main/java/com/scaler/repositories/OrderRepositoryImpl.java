package com.scaler.repositories;

import com.scaler.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository{
    List<Order> orders = new ArrayList<>();
    private static long idCounter = 0;
    @Override
    public Order save(Order order) {
        if(order.getId()==0)
            order.setId(++idCounter);
        orders.add(order);
        return order;
    }
}
