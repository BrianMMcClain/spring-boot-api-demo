package com.github.brianmmcclain.springbootapidemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestsTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /items
    @Test
    public void getInventoryShouldReturnAllItems() throws Exception {
        this.mockMvc.perform(get("/items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(5));
    }

    // GET /items/1
    @Test
    public void getItemShouldReturnItem() throws Exception {
        this.mockMvc.perform(get("/items/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Keyboard"))
            .andExpect(jsonPath("$.price").value(29.99))
            .andExpect(jsonPath("$.count").value(76));
    }

    // GET /items/-1
    @Test
    public void getNonexistingItemShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/items/-1"))
        .andExpect(status().isNotFound());
    }

    // POST /items
    @Test
    public void postWithoutIdShouldCreate() throws Exception {
        this.mockMvc.perform(post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Speakers\", \"price\": 39.99, \"count\": 33}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(6))
            .andExpect(jsonPath("$.name").value("Speakers"))
            .andExpect(jsonPath("$.price").value(39.99))
            .andExpect(jsonPath("$.count").value(33));        
    }

    // PUT /items/2
    @Test
    public void postWithIdShouldUpdate() throws Exception {
        this.mockMvc.perform(put("/items/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"price\": 17.99, \"count\": 73}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Mouse"))
            .andExpect(jsonPath("$.price").value(17.99))
            .andExpect(jsonPath("$.count").value(73));    
    }

    // DELETE /items/1
    @Test
    public void deleteItemShouldRemoveFromInventory() throws Exception {
        this.mockMvc.perform(delete("/items/1"))
        .andExpect(status().isOk());
    }

}