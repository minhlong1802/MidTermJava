package com.example.runnerz.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ItemJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ItemJsonDataLoader.class);
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public ItemJsonDataLoader(ItemRepository itemRepository, ObjectMapper objectMapper) {
        this.itemRepository = itemRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (itemRepository.count() == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/items.json")) {
                if (inputStream == null) {
                    throw new RuntimeException("Data file 'items.json' not found");
                }
                Items allItems = objectMapper.readValue(inputStream, Items.class);
                log.info("Reading {} items from JSON data and saving to database.", allItems.items().size());
                itemRepository.saveAll(allItems.items());
            } catch (IOException e) {
                log.error("Failed to read JSON data file", e);
                throw new RuntimeException("Failed to read JSON data file", e);
            }
        } else {
            log.info("Not loading items from JSON data because the collection already contains data");
        }
    }
}
