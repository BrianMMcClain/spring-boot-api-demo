package com.github.brianmmcclain.springbootapidemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            new Item(1, "Keyboard", 29.99, 76),
            new Item(2, "Mouse", 19.99, 43),
            new Item(3, "Monitor", 79.99, 7),
            new Item(4, "PC", 749.99, 2),
            new Item(5, "Headphones", 19.99, 14)
    ));

    /**
     * Get all items from the inventory, returned as a JSON array
     * @return All items from the inventory as a JSON array
     */
    @GetMapping("/items")
    public ArrayList<Item> getInventory() {
        // Spring Boot will handle the serialization of the ArrayList
        // to a JSON array, so we can return the list directly.
        return inventory;
    }
    
    /**
     * Get an item from the inventory by ID. Returned as JSON
     * @param id The id of the item to return
     * @return The id, name, and inventory of the item. If the item does not exist, return 404
     */
    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable("id") Long id) {
        Item item = findItem(id);
        
        if (item == null) {
            // If the item does not exist, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        } else {
            return item;
        }
        
    }

    /**
     * Update the price and/or count of an item in the inventory. Expects JSON.
     * 
     * Example: {"price": 12.99, "count": 12}
     * 
     * @param id ID of the item to update
     * @param req JSON object of fields to update
     * @return The updated item
     */
    @PostMapping("/items/{id}")
    public Item updateItem(@PathVariable("id") Long id, @RequestBody Map<String, String> req) {
        Item item = findItem(id);

        if (item == null) {
            // If the item does not exist, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        } else {
            // Iterate over the POSTed JSON fields
            for (String k : req.keySet()) {
                // Update the items price if a price field is provided
                if (k.equals("price")) {
                    item.setPrice(Double.parseDouble(req.get(k)));
                // Update the items count if a count field is provided
                } else if (k.equals("count")) {
                    item.setCount(Integer.parseInt(req.get(k)));
                }
            } 

            // Return the updated item
            return item;
        }
    }

    /**
     * Delete an item from the inventory.
     * If successful, this method returns a 200 status code if the item is deleted, 
     * or 404 if the item does not exist.
     * 
     * @param id The ID of the item to delete
     */
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        Item item = findItem(id);

        if (item == null) {
            // If the item does not exist, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        } else {
            // If the item exists, delete it from the list
            inventory.remove(item);
        }
    }

    /**
     * Helper function to look up an item from the inventory by ID
     * @param id ID of item to look up
     * @return Item object if it exists, null if it does not exist
     */
    private Item findItem(Long id) {
        // Search the list of items for the item of interest.
        // If the item does not exist, return null;
        return inventory.stream()
            .filter(i -> id.equals(i.getId()))
            .findAny()
            .orElse(null);
    }
}