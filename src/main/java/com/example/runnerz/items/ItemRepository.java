package com.example.runnerz.items;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class ItemRepository {
    private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);
    private final JdbcClient jdbcClient;

    public ItemRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Phương thức để tìm tất cả sản phẩm
    public List<Item> findAll() {
        return jdbcClient.sql("select * from items")
                .query(Item.class)
                .list();
    }

    // Phương thức để đếm số lượng sản phẩm
    public int count() {
        return jdbcClient.sql("select * from items").query().listOfRows().size();
    }

    // Phương thức để lưu tất cả sản phẩm
    public void saveAll(List<Item> items) {
        items.forEach(this::create);
    }

    // Phương thức để tạo sản phẩm mới
    public void create(Item item) {
        var updated = jdbcClient.sql("Insert into items(id,name,price,discountPrice,imageUrl,description,category,status) values(?,?,?,?,?,?,?,?)")
                .param(List.of(item.id(), item.name(), item.price(), item.discountPrice(), item.imageUrl(), item.description(), item.category().name(), item.status().name()))
                .update();
        Assert.state(updated == 1, "Unable to create item with id " + item.id());
    }

    // Phương thức để cập nhật sản phẩm theo id
    public void update(Item item, Integer id) {
        var updated = jdbcClient.sql("update items set name=?, price=?, discountPrice=?, imageUrl=?, description=?, category=?, status=? where id=?")
                .param(List.of(item.name(), item.price(), item.discountPrice(), item.imageUrl(), item.description(), item.category().name(), item.status().name(), id))
                .update();
        Assert.state(updated == 1, "Unable to update item with id " + id);
    }

    // Phương thức để xóa sản phẩm theo id
    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from items where id=:id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Unable to delete item with id " + id);
    }

    // Phương thức để xem chi tiết sản phẩm theo id
    public Item findById(Integer id) {
        return jdbcClient.sql("select * from items where id=:id")
                .param("id", id)
                .query(Item.class)
                .single();
    }

    // Phương thức để tìm kiếm sản phẩm theo tên
    public List<Item> findByName(String name) {
        return jdbcClient.sql("select * from items where name like :name")
                .param("name", "%" + name + "%")
                .query(Item.class)
                .list();
    }

    // Phương thức để tìm kiếm sản phẩm theo khoảng giá
    public List<Item> findByPriceRange(double minPrice, double maxPrice) {
        return jdbcClient.sql("select * from items where price between :minPrice and :maxPrice")
                .param("minPrice", minPrice)
                .param("maxPrice", maxPrice)
                .query(Item.class)
                .list();
    }
}

