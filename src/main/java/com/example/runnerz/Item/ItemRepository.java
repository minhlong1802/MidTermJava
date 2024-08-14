package com.example.runnerz.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ItemRepository {
    private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to find all items
    public List<Item> findAll() {
        String sql = "SELECT * FROM item";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToItem(rs));
    }

    // Method to count the number of items
    public int count() {
        String sql = "SELECT COUNT(*) FROM item";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Method to save all items
    public void saveAll(List<Item> items) {
        for (Item item : items) {
            create(item);
        }
    }

    // Method to create an item
    public void create(Item item) {
        String sql = "INSERT INTO item (id, name, price, discountPrice, imageUrl, description, category, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int updated = jdbcTemplate.update(sql, item.id(), item.name(), item.price(),
                item.discountPrice() != null ? item.discountPrice() : BigDecimal.ZERO,
                item.imageUrl(), item.description(), item.category().name(), item.status().name());
        if (updated != 1) {
            log.error("Unable to create item with id {}", item.id());
            throw new RuntimeException("Unable to create item with id " + item.id());
        }
    }

    // Method to update an item
    public void update(Item item, Integer id) {
        String sql = "UPDATE item SET name = ?, price = ?, discountPrice = ?, imageUrl = ?, description = ?, category = ?, status = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, item.name(), item.price(),
                item.discountPrice() != null ? item.discountPrice() : BigDecimal.ZERO,
                item.imageUrl(), item.description(), item.category().name(), item.status().name(), id);
        if (updated != 1) {
            log.error("Unable to update item with id {}", id);
            throw new RuntimeException("Unable to update item with id " + id);
        }
    }

    // Method to delete an item
    public void delete(Integer id) {
        String sql = "DELETE FROM item WHERE id = ?";
        int updated = jdbcTemplate.update(sql, id);
        if (updated != 1) {
            log.error("Unable to delete item with id {}", id);
            throw new RuntimeException("Unable to delete item with id " + id);
        }
    }

    // Method to find an item by id
    public Item findById(Integer id) {
        String sql = "SELECT * FROM item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRowToItem(rs), id);
    }

    // Method to find items by name
    public List<Item> findByName(String name) {
        String sql = "SELECT * FROM item WHERE name LIKE ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToItem(rs), "%" + name + "%");
    }

    // Method to find items by price range
    public List<Item> findByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM item WHERE price BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToItem(rs), minPrice, maxPrice);
    }

    // Helper method to map a result set row to an Item object
    private Item mapRowToItem(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getBigDecimal("discountPrice"),
                rs.getString("imageUrl"),
                rs.getString("description"),
                Item.Category.valueOf(rs.getString("category")),
                Item.Status.valueOf(rs.getString("status"))
        );
    }
}
