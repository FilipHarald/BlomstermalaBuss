-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: localhost    Database: blomstermala
-- ------------------------------------------------------
-- Server version	5.6.24

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
-- Table structure for table `bokning`
--

DROP TABLE IF EXISTS `bokning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bokning` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL,
  `kund` varchar(10) NOT NULL,
  `tur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kund` (`kund`),
  KEY `tur` (`tur`),
  CONSTRAINT `bokning_ibfk_1` FOREIGN KEY (`kund`) REFERENCES `kund` (`personnummer`),
  CONSTRAINT `bokning_ibfk_2` FOREIGN KEY (`tur`) REFERENCES `tur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bokning`
--

LOCK TABLES `bokning` WRITE;
/*!40000 ALTER TABLE `bokning` DISABLE KEYS */;
/*!40000 ALTER TABLE `bokning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kund`
--

DROP TABLE IF EXISTS `kund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kund` (
  `personnummer` varchar(10) NOT NULL,
  `namn` varchar(50) NOT NULL,
  `telefonnummer` varchar(20) DEFAULT NULL,
  `mail` varchar(255) NOT NULL,
  `losenord` varchar(60) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`personnummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kund`
--

LOCK TABLES `kund` WRITE;
/*!40000 ALTER TABLE `kund` DISABLE KEYS */;
/*!40000 ALTER TABLE `kund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ort`
--

DROP TABLE IF EXISTS `ort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ort` (
  `namn` varchar(30) NOT NULL,
  PRIMARY KEY (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ort`
--

LOCK TABLES `ort` WRITE;
/*!40000 ALTER TABLE `ort` DISABLE KEYS */;
/*!40000 ALTER TABLE `ort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paketresa`
--

DROP TABLE IF EXISTS `paketresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paketresa` (
  `bokning` int(11) NOT NULL,
  `ordning` int(11) NOT NULL,
  `tur` int(11) NOT NULL,
  PRIMARY KEY (`bokning`,`tur`),
  KEY `tur` (`tur`),
  CONSTRAINT `paketresa_ibfk_1` FOREIGN KEY (`bokning`) REFERENCES `bokning` (`id`),
  CONSTRAINT `paketresa_ibfk_2` FOREIGN KEY (`tur`) REFERENCES `tur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paketresa`
--

LOCK TABLES `paketresa` WRITE;
/*!40000 ALTER TABLE `paketresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `paketresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tur`
--

DROP TABLE IF EXISTS `tur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kostnad` int(11) NOT NULL,
  `avresetid` time NOT NULL,
  `avresedag` int(11) NOT NULL,
  `ankomsttid` time NOT NULL,
  `ankomstdag` int(11) NOT NULL,
  `kapacitet` int(11) NOT NULL,
  `avreseort` varchar(30) NOT NULL,
  `ankomstort` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `avreseort` (`avreseort`),
  KEY `ankomstort` (`ankomstort`),
  CONSTRAINT `tur_ibfk_1` FOREIGN KEY (`avreseort`) REFERENCES `ort` (`namn`),
  CONSTRAINT `tur_ibfk_2` FOREIGN KEY (`ankomstort`) REFERENCES `ort` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tur`
--

LOCK TABLES `tur` WRITE;
/*!40000 ALTER TABLE `tur` DISABLE KEYS */;
/*!40000 ALTER TABLE `tur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-21 14:07:32
