package com.example.runnerz.items;

public record Item(
        int id,
        String name,
        double price,
        double discountPrice,
        String imageUrl,
        String description,
        Category category,
        Status status
) {
    // Enum for Category
    public enum Category {
        CATEGORY_ONE,
        CATEGORY_TWO,
        CATEGORY_THREE
    }

    // Enum for Status
    public enum Status {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }
}
