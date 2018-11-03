-- MySQL dump 10.13  Distrib 5.7.9, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: echarts
-- ------------------------------------------------------
-- Server version	5.5.55-0ubuntu0.14.04.1

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
-- Table structure for table `t_city`
--

DROP TABLE IF EXISTS `t_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_city` (
  `m_id` int(11) DEFAULT NULL,
  `m_provenceid` int(11) DEFAULT NULL,
  `m_cityname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_city`
--

LOCK TABLES `t_city` WRITE;
/*!40000 ALTER TABLE `t_city` DISABLE KEYS */;
INSERT INTO `t_city` VALUES (1,1,'烟台'),(2,2,'济南');
/*!40000 ALTER TABLE `t_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_detail`
--

DROP TABLE IF EXISTS `t_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_detail` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `m_patentid` varchar(50) DEFAULT NULL,
  `m_date` date DEFAULT NULL,
  `m_cityid` int(11) DEFAULT NULL,
  `m_provenceid` int(11) DEFAULT NULL,
  `m_unitid` int(11) DEFAULT NULL,
  `m_typeid` int(11) DEFAULT NULL,
  `m_status` tinyint(4) DEFAULT NULL,
  `m_applier` varchar(100) DEFAULT NULL,
  `m_inventor` varchar(100) DEFAULT NULL,
  `m_agent` varchar(255) DEFAULT NULL,
  `m_address` varchar(255) DEFAULT NULL,
  `m_introduce` blob,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_detail`
--

LOCK TABLES `t_detail` WRITE;
/*!40000 ALTER TABLE `t_detail` DISABLE KEYS */;
INSERT INTO `t_detail` VALUES (1,'123qwe','2017-04-30',1,1,1,1,1,'weq','qwe','qew','qwe',NULL),(2,'345fdg','2015-04-30',2,2,2,2,0,'fsd','ert','erg','ert',NULL),(3,'453ter','2012-04-30',1,1,1,1,1,'ewr','342','432','645',NULL),(4,'ggrw434','2012-04-30',1,1,1,3,1,'sd','awd','adw','das',NULL),(5,'dsff','2012-04-30',1,1,1,1,0,'sd','dsa','ads','das',NULL),(6,'234fg','2012-04-30',2,2,2,2,1,'fs','fsd','fsd','fwe',NULL),(7,'34tfvd','2015-04-30',1,1,1,1,0,'sq','wq','wq','qw',NULL);
/*!40000 ALTER TABLE `t_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_provence`
--

DROP TABLE IF EXISTS `t_provence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_provence` (
  `m_provenceid` int(11) NOT NULL AUTO_INCREMENT,
  `m_provencename` varchar(255) DEFAULT NULL,
  `m_capital` int(11) DEFAULT NULL,
  PRIMARY KEY (`m_provenceid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_provence`
--

LOCK TABLES `t_provence` WRITE;
/*!40000 ALTER TABLE `t_provence` DISABLE KEYS */;
INSERT INTO `t_provence` VALUES (1,'山东',NULL),(2,'河北',NULL);
/*!40000 ALTER TABLE `t_provence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type`
--

DROP TABLE IF EXISTS `t_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_type` (
  `m_typeid` int(11) NOT NULL AUTO_INCREMENT,
  `m_typename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`m_typeid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type`
--

LOCK TABLES `t_type` WRITE;
/*!40000 ALTER TABLE `t_type` DISABLE KEYS */;
INSERT INTO `t_type` VALUES (1,'种类1'),(2,'种类2'),(3,'种类3');
/*!40000 ALTER TABLE `t_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type_unit`
--

DROP TABLE IF EXISTS `t_type_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_type_unit` (
  `m_id` int(11) NOT NULL,
  `m_typeid` int(11) DEFAULT NULL,
  `m_unitid` int(11) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type_unit`
--

LOCK TABLES `t_type_unit` WRITE;
/*!40000 ALTER TABLE `t_type_unit` DISABLE KEYS */;
INSERT INTO `t_type_unit` VALUES (1,1,1),(2,2,2),(3,3,1);
/*!40000 ALTER TABLE `t_type_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_unit`
--

DROP TABLE IF EXISTS `t_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_unit` (
  `m_unitid` int(11) NOT NULL AUTO_INCREMENT,
  `m_unitname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`m_unitid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_unit`
--

LOCK TABLES `t_unit` WRITE;
/*!40000 ALTER TABLE `t_unit` DISABLE KEYS */;
INSERT INTO `t_unit` VALUES (1,'单位1'),(2,'单位2');
/*!40000 ALTER TABLE `t_unit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-22 16:23:37
