package com.example.runnerz.Item;

import java.math.BigDecimal;

public record Item(
        int id,
        String name,
        BigDecimal price,
        BigDecimal discountPrice,
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

    public Item {
        if (price == null) {
            throw new IllegalArgumentException("Price must be specified");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        // Default discountPrice to zero if it's null
        if (discountPrice == null) {
            discountPrice = BigDecimal.ZERO;
        }

        if (discountPrice.compareTo(price) > 0) {
            throw new IllegalArgumentException("Discounted price must be smaller than price");
        }
    }
}
