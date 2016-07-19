-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema book_library
-- -----------------------------------------------------
-- Book library consists of the list of book item's details, user account details and administrator details.

-- -----------------------------------------------------
-- Schema book_library
--
-- Book library consists of the list of book item's details, user account details and administrator details.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `book_library` DEFAULT CHARACTER SET utf8 ;
USE `book_library` ;

-- -----------------------------------------------------
-- Table `book_library`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`User` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `surname` VARCHAR(200) NOT NULL,
  `username` VARCHAR(250) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `e_mail` VARCHAR(250) NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  `access_privilege` ENUM('admin', 'user') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `e-mail_UNIQUE` (`e_mail` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Genre` (
  `genre_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`genre_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Book` (
  `book_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ISBN` VARCHAR(45) NULL,
  `title` VARCHAR(500) NOT NULL,
  `genre_id` INT UNSIGNED NOT NULL,
  `volume` INT UNSIGNED NULL,
  `abstract` TEXT NOT NULL,
  `language` VARCHAR(100) NOT NULL,
  `count` INT UNSIGNED NOT NULL,
  `edition_year` VARCHAR(20) NOT NULL,
  `pages` INT UNSIGNED NOT NULL,
  `country_of_edition` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`book_id`),
  INDEX `genre_id_idx` (`genre_id` ASC),
  CONSTRAINT `fk_book_genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `book_library`.`Genre` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Author` (
  `author_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NULL,
  `web_page` VARCHAR(250) NULL,
  `biography` VARCHAR(250) NULL,
  PRIMARY KEY (`author_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Book_Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Book_Author` (
  `book_id` INT UNSIGNED NOT NULL,
  `author_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`book_id`, `author_id`),
  INDEX `author_id_idx` (`author_id` ASC),
  INDEX `book_id_idx` (`book_id` ASC),
  CONSTRAINT `book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`Book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `book_library`.`Author` (`author_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Pick_Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Pick_Book` (
  `pick_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  `picking_date` DATETIME NOT NULL,
  `return_date` DATETIME NOT NULL,
  PRIMARY KEY (`pick_id`),
  INDEX `book_id_idx` (`book_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_pickbook_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`Book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `kf_pickbook_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `book_library`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Pending`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Pending` (
  `pending_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `book_id` INT UNSIGNED NOT NULL,
  `pending_time` DATETIME NOT NULL,
  PRIMARY KEY (`pending_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `book_id_idx` (`book_id` ASC),
  CONSTRAINT `fk_pending_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `book_library`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pending_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`Book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_library`.`Media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`Media` (
  `media_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `media` VARCHAR(255) NOT NULL,
  `media_type` ENUM('photo', 'video') NOT NULL,
  `book_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`media_id`),
  INDEX `fk_Media_book_id_idx` (`book_id` ASC),
  CONSTRAINT `fk_Media_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`Book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
