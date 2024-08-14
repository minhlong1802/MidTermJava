package com.example.runnerz.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://192.168.0.104:5500/crudJavaS.html", allowCredentials = "true")
public class ItemController {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    List<Item> findAll() {
        return itemRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void create(@RequestBody Item item) {
        itemRepository.create(item);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{id}")
    void update(@RequestBody Item item, @PathVariable Integer id) {
        itemRepository.update(item, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        itemRepository.delete(id);
    }

    // Phương thức để xem chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    Item findById(@PathVariable Integer id) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(id));
        if(item.isEmpty()){
            throw new ItemNotFoundException();
        }
        return item.get();
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
