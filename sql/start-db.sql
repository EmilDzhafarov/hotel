SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP DATABASE IF EXISTS hotel;
CREATE DATABASE hotel;
USE hotel;

CREATE TABLE IF NOT EXISTS `hotel`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `hotel_room_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `staying_days` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `hotel_room_id_idx` (`hotel_room_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `hotel_room_id`
  FOREIGN KEY (`hotel_room_id`)
  REFERENCES `hotel`.`hotel_rooms` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `hotel`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Role may be: manager == 1 and client = 2
CREATE TABLE IF NOT EXISTS `hotel`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role` ENUM('1', '2') NOT NULL,
  `registrtion_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)
);

CREATE TABLE IF NOT EXISTS `hotel`.`bills` (
  `order_id` INT(11) NOT NULL AUTO_INCREMENT,
  `price` DECIMAL UNSIGNED NOT NULL,
  `is_paid` TINYINT(4) NOT NULL,
  `creation_time` DATETIME NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `order_id`
  FOREIGN KEY (`order_id`)
  REFERENCES `hotel`.`orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- This table keeps information about hotel rooms.
-- apartment_class can be only:
--     1 ==> President
--     2 ==> Business
--     3 ==> Apartment
--     4 ==> De Lux
--
-- status can be only:
--     1 ==> free
--     2 ==> reserved
--     3 ==> busy
--     4 ==> unavailable
CREATE TABLE IF NOT EXISTS `hotel`.`hotel_rooms` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `number` INT(10) UNSIGNED NOT NULL,
  `number_of_places` INT(10) UNSIGNED NOT NULL,
  `apartment_class` ENUM('1', '2', '3', '4') NOT NULL,
  `price_per_day` DECIMAL UNSIGNED NOT NULL,
  `status` ENUM('1', '2', '3', '4') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC)
);

-- ATTENTION!!! By default it is off!!!
SET GLOBAL EVENT_SCHEDULER = 1;

-- This event is designed for checking and deleting old orders
-- (orders that were created 2 days ago and still haven't been paid).
CREATE EVENT IF NOT EXISTS delete_old_bills
ON SCHEDULE EVERY 1 DAY STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY)
DO
DELETE FROM orders WHERE id IN
(SELECT order_id FROM bills
WHERE (creation_time + INTERVAL 2 DAY < NOW()) AND is_paid = 0);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;