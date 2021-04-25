-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: manufacturerservices
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `SID` int NOT NULL AUTO_INCREMENT,
  `ServiceID` varchar(9) DEFAULT NULL,
  `Name` varchar(65) DEFAULT NULL,
  `Speciality` varchar(65) DEFAULT NULL,
  `Description` varchar(150) DEFAULT NULL,
  `MFRID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (20,'MS0000020','3DD10AAF2A64ACD87A22C6436F3179DC8EF58B7AFAD6DCA89FC247C3B6C9CA35','A30B285B8F2329648DD6D1B1659C06FDCD72889B18B37E8C2F95A3891B19DF7B','We make electronics based products for an affordable price','MN0000001'),(22,'MS0000021','C083DFB4B33F0176639FEEA7CF6623694F9287CBE501D29935E5C1A8AF207CD1','15D80D027CD0B2A3BAE5C03E089F9F5C391D8407571E660AAC98D334C15684DB','We craft excellent Wooden crafts','MN0000002');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sname`
--

DROP TABLE IF EXISTS `sname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sname` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nKey` varchar(50) DEFAULT NULL,
  `Value` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sname`
--

LOCK TABLES `sname` WRITE;
/*!40000 ALTER TABLE `sname` DISABLE KEYS */;
INSERT INTO `sname` VALUES (16,'TE Connect','3DD10AAF2A64ACD87A22C6436F3179DC8EF58B7AFAD6DCA89FC247C3B6C9CA35'),(17,'TEL Global ','B4F4CD8417A0E15D220121AF2774E716F2B543D3EFD1A2B8EB203725ACB9BDA1'),(18,'TE Connect','3DD10AAF2A64ACD87A22C6436F3179DC8EF58B7AFAD6DCA89FC247C3B6C9CA35'),(19,'Woodsy','C083DFB4B33F0176639FEEA7CF6623694F9287CBE501D29935E5C1A8AF207CD1');
/*!40000 ALTER TABLE `sname` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sspec`
--

DROP TABLE IF EXISTS `sspec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sspec` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sKey` varchar(50) DEFAULT NULL,
  `Value` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sspec`
--

LOCK TABLES `sspec` WRITE;
/*!40000 ALTER TABLE `sspec` DISABLE KEYS */;
INSERT INTO `sspec` VALUES (9,'Electronics','A30B285B8F2329648DD6D1B1659C06FDCD72889B18B37E8C2F95A3891B19DF7B'),(10,'Tele Communication','EB624245A1B3C85CC23AA67376985376DEA87140CEDA79333B3308D583EC416F'),(11,'Electronics','A30B285B8F2329648DD6D1B1659C06FDCD72889B18B37E8C2F95A3891B19DF7B'),(12,'Wooden Items','15D80D027CD0B2A3BAE5C03E089F9F5C391D8407571E660AAC98D334C15684DB');
/*!40000 ALTER TABLE `sspec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'manufacturerservices'
--
/*!50003 DROP FUNCTION IF EXISTS `getManuID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getManuID`() RETURNS varchar(9) CHARSET utf8mb4
    DETERMINISTIC
BEGIN

 

    declare maxID int;
    declare genID varchar(9);
    declare stringID varchar(7);
    
    select max(SID) into maxID from services;
    
    set stringID := convert(maxID+1,char(7));
    set stringID := lpad(stringID,7,0);
    set genID := concat('MS',stringID);

    RETURN genID;

 

 

 


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-25 17:17:06
