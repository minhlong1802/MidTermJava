package com.example.runnerz.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ItemJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ItemJsonDataLoader.class);
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;
    public ItemJsonDataLoader(ItemRepository itemRepository, ObjectMapper objectMapper){
        this.itemRepository=itemRepository;
        this.objectMapper=objectMapper;
    }
    @Override
    public void run(String... args) throws Exception{
        if(itemRepository.count()==0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/items.json")) {
                Items allRuns = objectMapper.readValue(inputStream, Items.class);
                log.info("Reading {} items from JSON data and saving to database.", allRuns.items().size());
                itemRepository.saveAll(allRuns.items());
            } catch (IOException e) {
                throw new RuntimeException("Fail to read JSON data file", e);
            }
        }else{
            log.info("Not loading Items from JSON data because the collections contain data");
        }
    }
}
