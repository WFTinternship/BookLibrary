-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: book_library
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `author_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `birth_year` int(10) NOT NULL,
  `birth_city` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `web_page` varchar(250) DEFAULT NULL,
  `biography` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`author_id`),
  UNIQUE KEY `authorUniqueKey` (`name`,`surname`,`birth_year`,`birth_city`)
) ENGINE=InnoDB AUTO_INCREMENT=980 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (976,'Paul','Deitel',1965,'California','','',''),(977,'Herbert','Shildt',1951,'Chicago','','http://www.herbschildt.com/',''),(978,'Harvey','Deitel',1955,'Chicago','','',''),(979,'Martin','Gruber',1970,'London','','','');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `book_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ISBN` varchar(45) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `genre_id` int(10) unsigned NOT NULL,
  `volume` int(10) unsigned DEFAULT '0',
  `abstract` text NOT NULL,
  `language` varchar(100) NOT NULL,
  `count` int(10) unsigned NOT NULL,
  `edition_year` varchar(20) NOT NULL,
  `pages` int(10) unsigned NOT NULL,
  `country_of_edition` varchar(300) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `genre_id_idx` (`genre_id`),
  CONSTRAINT `fk_book_genre_id` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=631 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (628,NULL,'Java How to Program',1237,0,'How to effectively code in Java','english',0,'2015',987,'USA'),(629,NULL,'Java The Complete reference',1237,0,'Java reference book','english',5,'2014',957,'USA'),(630,NULL,'Understanding SQL',1238,2,'SQL basic knowledge','english',3,'2012',291,'USA');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_author`
--

DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_author` (
  `book_id` int(10) unsigned NOT NULL,
  `author_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`book_id`,`author_id`),
  KEY `author_id_idx` (`author_id`),
  KEY `book_id_idx` (`book_id`),
  CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_author`
--

LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */;
INSERT INTO `book_author` VALUES (628,976),(629,977),(630,979);
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `genre_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `genre` varchar(150) NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `genre_UNIQUE` (`genre`)
) ENGINE=InnoDB AUTO_INCREMENT=1239 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1237,'Java'),(1236,'OOP'),(1238,'SQL');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `media_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `media` varchar(255) NOT NULL,
  `media_type_id` int(10) unsigned NOT NULL,
  `book_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`media_id`),
  UNIQUE KEY `media_UNIQUE` (`media`),
  KEY `fk_Media_book_id_idx` (`book_id`),
  KEY `fk_media_media_type_id_idx` (`media_type_id`),
  CONSTRAINT `fk_Media_book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_media_media_type_id` FOREIGN KEY (`media_type_id`) REFERENCES `media_type` (`mediaType_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (190,'C:\\Users\\Workfront\\IdeaProjects\\BookLibrary\\target\\library-1.0-SNAPSHOT\\resources\\upload\\download1.jpg',225,628),(191,'C:\\Users\\Workfront\\IdeaProjects\\BookLibrary\\target\\library-1.0-SNAPSHOT\\resources\\upload\\download3.jpg',225,630),(194,'C:\\Users\\Workfront\\IdeaProjects\\BookLibrary\\target\\library-1.0-SNAPSHOT\\resources\\upload\\Java htp 1.jpg',225,628),(197,'C:\\Users\\Workfront\\IdeaProjects\\BookLibrary\\target\\library-1.0-SNAPSHOT\\resources\\upload\\Java cr 1.jpg',225,628);
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_type`
--

DROP TABLE IF EXISTS `media_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_type` (
  `mediaType_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `media_type` varchar(250) NOT NULL,
  PRIMARY KEY (`mediaType_id`),
  UNIQUE KEY `media_type_UNIQUE` (`media_type`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_type`
--

LOCK TABLES `media_type` WRITE;
/*!40000 ALTER TABLE `media_type` DISABLE KEYS */;
INSERT INTO `media_type` VALUES (225,'link'),(226,'photo'),(227,'video');
/*!40000 ALTER TABLE `media_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pending`
--

DROP TABLE IF EXISTS `pending`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pending` (
  `pending_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `book_id` int(10) unsigned NOT NULL,
  `pending_time` datetime NOT NULL,
  PRIMARY KEY (`pending_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `book_id_idx` (`book_id`),
  CONSTRAINT `fk_pending_book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_pending_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pending`
--

LOCK TABLES `pending` WRITE;
/*!40000 ALTER TABLE `pending` DISABLE KEYS */;
INSERT INTO `pending` VALUES (97,394,628,'2016-09-30 01:39:26');
/*!40000 ALTER TABLE `pending` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pick_book`
--

DROP TABLE IF EXISTS `pick_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pick_book` (
  `pick_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `picking_date` datetime NOT NULL,
  `return_date` datetime NOT NULL,
  PRIMARY KEY (`pick_id`),
  KEY `book_id_idx` (`book_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `fk_pickbook_book_id` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `kf_pickbook_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pick_book`
--

LOCK TABLES `pick_book` WRITE;
/*!40000 ALTER TABLE `pick_book` DISABLE KEYS */;
INSERT INTO `pick_book` VALUES (109,628,393,'2016-09-30 01:37:58','2016-10-10 01:37:58');
/*!40000 ALTER TABLE `pick_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(150) NOT NULL,
  `address` varchar(150) NOT NULL,
  `e_mail` varchar(256) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `access_privilege` enum('admin','user') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `e-mail_UNIQUE` (`e_mail`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (393,'Sona','Mikayelyan','sonamikayelyan','9a5a846499c7f4597a8430d1c3df5478d84160d89b68cdfebc4a847e213504d2','sbla','miksona@yahoo.com','099148088','user'),(394,'Anna','Asmangulyan','annaasmangulyan','55579b557896d0ce1764c47fed644f9b35f58bad620674af23f356d80ed0c503','addd','anna@yahoo.com','0654','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-30 14:17:17
