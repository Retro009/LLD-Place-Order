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
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        Map<MenuItem,Integer> menuItem = new HashMap<>();
        for(Long i:orderedItems.keySet()){
            if(menuItemRepository.findById(i).isEmpty())
                throw new InvalidMenuItem("Menu Item Not Found");
            menuItem.put(menuItemRepository.findById(i).get(),orderedItems.get(i));
        }
        Order order = new Order();
        CustomerSession customerSession;
        Optional<CustomerSession> activeCustomerSessionByUserId = customerSessionRepository.findActiveCustomerSessionByUserId(userId);

        if(activeCustomerSessionByUserId.isEmpty()){
            customerSession = new CustomerSession();
            customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
        }else
            customerSession = activeCustomerSessionByUserId.get();

        order.setCustomerSession(customerSession);
        order.setOrderedItems(menuItem);

        return order;
    }
}
