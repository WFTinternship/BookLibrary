-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema book_library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema book_library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `book_library` DEFAULT CHARACTER SET utf8 ;
USE `book_library` ;

-- -----------------------------------------------------
-- Table `book_library`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`author` (
  `author_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NULL DEFAULT NULL,
  `web_page` VARCHAR(250) NULL DEFAULT NULL,
  `biography` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`author_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`genre` (
  `genre_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE INDEX `genre_UNIQUE` (`genre` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`book` (
  `book_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ISBN` VARCHAR(45) NULL DEFAULT NULL,
  `title` VARCHAR(500) NOT NULL,
  `genre_id` INT(10) UNSIGNED NOT NULL,
  `volume` INT(10) UNSIGNED NULL DEFAULT NULL,
  `abstract` TEXT NOT NULL,
  `language` VARCHAR(100) NOT NULL,
  `count` INT(10) UNSIGNED NOT NULL,
  `edition_year` VARCHAR(20) NOT NULL,
  `pages` INT(10) UNSIGNED NOT NULL,
  `country_of_edition` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC),
  INDEX `genre_id_idx` (`genre_id` ASC),
  CONSTRAINT `fk_book_genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `book_library`.`genre` (`genre_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`book_author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`book_author` (
  `book_id` INT(10) UNSIGNED NOT NULL,
  `author_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`book_id`, `author_id`),
  INDEX `author_id_idx` (`author_id` ASC),
  INDEX `book_id_idx` (`book_id` ASC),
  CONSTRAINT `author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `book_library`.`author` (`author_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`book` (`book_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`media_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`media_type` (
  `mediaType_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `media_type` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`mediaType_id`),
  UNIQUE INDEX `media_type_UNIQUE` (`media_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`media` (
  `media_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `media` VARCHAR(255) NOT NULL,
  `media_type_id` INT(10) UNSIGNED NOT NULL,
  `book_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`media_id`),
  UNIQUE INDEX `media_UNIQUE` (`media` ASC),
  INDEX `fk_Media_book_id_idx` (`book_id` ASC),
  INDEX `fk_media_media_type_id_idx` (`media_type_id` ASC),
  CONSTRAINT `fk_Media_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`book` (`book_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_media_media_type_id`
    FOREIGN KEY (`media_type_id`)
    REFERENCES `book_library`.`media_type` (`mediaType_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`user` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `username` VARCHAR(256) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `e_mail` VARCHAR(256) NOT NULL,
  `phone` VARCHAR(100) NOT NULL,
  `access_privilege` ENUM('admin', 'user') NOT NULL,
  `confirmation_status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `e-mail_UNIQUE` (`e_mail` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`pending`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`pending` (
  `pending_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `book_id` INT(10) UNSIGNED NOT NULL,
  `pending_time` DATETIME NOT NULL,
  PRIMARY KEY (`pending_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `book_id_idx` (`book_id` ASC),
  CONSTRAINT `fk_pending_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`book` (`book_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pending_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `book_library`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `book_library`.`pick_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_library`.`pick_book` (
  `pick_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` INT(10) UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `picking_date` DATETIME NOT NULL,
  `return_date` DATETIME NOT NULL,
  PRIMARY KEY (`pick_id`),
  INDEX `book_id_idx` (`book_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_pickbook_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `book_library`.`book` (`book_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `kf_pickbook_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `book_library`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
