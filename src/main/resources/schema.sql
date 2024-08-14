CREATE TABLE IF NOT EXISTS item (
    id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    discount_price DECIMAL(10, 2),
    image_url VARCHAR(255),
    description TEXT,
    category ENUM('CATEGORY_ONE', 'CATEGORY_TWO', 'CATEGORY_THREE') NOT NULL,
    status ENUM('AVAILABLE', 'OUT_OF_STOCK', 'DISCONTINUED') NOT NULL
    PRIMARY KEY (id)
);
