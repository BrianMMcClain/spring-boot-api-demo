package com.github.brianmmcclain.springbootapidemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InventoryController {
    
    /**
     * To simplify the demo, we'll use a simple list of items
     * in-memory to track our inventory. In reality, you'd be better
     * using something like Spring JPA
     */
    ArrayList<Item> inventory = new ArrayList<Item>(
        Arrays.asList(
            new Item(1, "Keyboard", 76),
            new Item(2, "Mouse", 43),
            new Item(3, "Monitor", 7),
            new Item(4, "PC", 2),
            new Item(5, "Headphones", 14)
    ));

    
    /**
     * 
     * @param id The id of the item to return
     * @return The id, name, and inventory of the item. If the item does not exist, return 404
     */
    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") Long id) {

        // Search the list of items for the item of interest
        Item item = inventory.stream()
            .filter(i -> id.equals(i.getId()))
            .findAny()
            .orElse(null);

        // If the item doesn't exist, return 404
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }

        // Otherwise, return the item
        return item;
    }
}