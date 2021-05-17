
CREATE TABLE Users (
    `id`  INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(32),
    `lastName` VARCHAR(64),
    `age` INT,
    `address` VARCHAR(64),
    `city` VARCHAR(64),
    `country` VARCHAR(64));
