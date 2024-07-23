package com.scaler.repositories;

import com.scaler.models.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepositoryImpl implements MenuItemRepository{
    List<MenuItem> menuItems = new ArrayList<>();
    @Override
    public MenuItem add(MenuItem menuItem) {
        menuItems.add(menuItem);
        return menuItem;
    }

    @Override
    public Optional<MenuItem> findById(long id) {
        return menuItems.stream().filter(menuItem -> menuItem.getId() == id).findFirst();
    }
}
