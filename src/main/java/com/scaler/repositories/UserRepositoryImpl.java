package com.scaler.repositories;

import com.scaler.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    List<User> users = new ArrayList<>();
    private static long idCounter = 0;
    @Override
    public Optional<User> findById(long id) {
        return users.stream().filter(user -> user.getId()==id).findFirst();
    }

    @Override
    public User save(User user) {
        if(user.getId()==0)
            user.setId(++idCounter);
        users.add(user);
        return user;
    }
}
