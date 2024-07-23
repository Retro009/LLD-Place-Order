package com.scaler.repositories;

import com.scaler.models.CustomerSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerSessionRepositoryImpl implements CustomerSessionRepository{
    List<CustomerSession> customerSessions = new ArrayList<>();
    @Override
    public CustomerSession save(CustomerSession customerSession) {
        customerSessions.add(customerSession);
        return customerSession;
    }

    @Override
    public Optional<CustomerSession> findActiveCustomerSessionByUserId(long userId) {
        return customerSessions.stream().filter(customerSession -> customerSession.getId() == userId && customerSession.isActive()).findFirst();
    }
}
