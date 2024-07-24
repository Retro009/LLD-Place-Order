package com.scaler.services;

import com.scaler.exceptions.InvalidMenuItem;
import com.scaler.exceptions.UserNotFoundException;
import com.scaler.models.*;
import com.scaler.repositories.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService{
    CustomerSessionRepository customerSessionRepository;
    MenuItemRepository menuItemRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public OrderServiceImpl(CustomerSessionRepository customerSessionRepository, MenuItemRepository menuItemRepository, OrderRepository orderRepository, UserRepository userRepository){
        this.customerSessionRepository = customerSessionRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Order placeOrder(long userId, Map<Long, Integer> orderedItems) throws UserNotFoundException, InvalidMenuItem {
        Optional<CustomerSession> optionalCustomerSession = customerSessionRepository.findActiveCustomerSessionByUserId(userId);
        CustomerSession customerSession;
        if(optionalCustomerSession.isEmpty()){
            customerSession = new CustomerSession();
            User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found"));
            customerSession.setUser(user);
            customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
            customerSessionRepository.save(customerSession);
        }else
            customerSession = optionalCustomerSession.get();
        Map<MenuItem,Integer> orderedMenuItem = new HashMap<>();
        for(Map.Entry<Long, Integer> entry: orderedItems.entrySet()){
            Long itemId = entry.getKey();
            Integer quantity = entry.getValue();

            MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(()-> new InvalidMenuItem("Invalid Menu Item"));
            orderedMenuItem.put(menuItem,quantity);
        }
        Order order = new Order();
        order.setOrderedItems(orderedMenuItem);
        order.setCustomerSession(customerSession);

        return orderRepository.save(order);
    }
}
