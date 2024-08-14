package com.example.runnerz.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    List<Item> findAll() {
        return itemRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Item item) {
        itemRepository.create(item);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Item item, @PathVariable Integer id) {
        itemRepository.update(item, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        itemRepository.delete(id);
    }

    // Phương thức để xem chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    Item findById(@PathVariable Integer id) {
        return itemRepository.findById(id);
    }

    // Phương thức để tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    List<Item> findByName(@RequestParam String name) {
        return itemRepository.findByName(name);
    }

    // Phương thức để tìm kiếm sản phẩm theo khoảng giá
    @GetMapping("/price-range")
    List<Item> findByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return itemRepository.findByPriceRange(minPrice, maxPrice);
    }
}
