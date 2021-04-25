-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: campaign_service
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `backs`
--

DROP TABLE IF EXISTS `backs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `backs` (
  `conceptID` varchar(9) NOT NULL,
  `backerID` varchar(9) NOT NULL,
  `pledgedAmnt` double NOT NULL,
  PRIMARY KEY (`conceptID`,`backerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backs`
--

LOCK TABLES `backs` WRITE;
/*!40000 ALTER TABLE `backs` DISABLE KEYS */;
INSERT INTO `backs` VALUES ('CP0000002','CM0000001',6000),('CP0000002','CM0000006',2000),('CP0000003','CM0000002',4000),('CP0000003','CM0000003',5000),('CP0000003','CM0000007',10000),('CP0000003','CM0000034',4000),('CP0000004','CM0000001',2000),('CP0000004','CM0000007',4500),('CP0000005','CM0000001',2000),('CP0000005','CM0000012',9000),('CP0000006','CM0000003',5000),('CP0000008','CM0000001',15000),('CP0000008','CM0000003',10000),('CP0000008','CM0000007',10000),('CP0000008','CM0000010',15000),('CP0000014','CM0000001',7000),('CP0000015','CM0000001',5000),('CP0000015','CM0000002',6000),('CP0000016','CM0000002',6000),('CP0000019','CM0000002',6000);
/*!40000 ALTER TABLE `backs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concept`
--

DROP TABLE IF EXISTS `concept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `concept` (
  `conceptID` int(11) NOT NULL AUTO_INCREMENT,
  `conceptCode` varchar(9) NOT NULL,
  `conceptName` varchar(65) NOT NULL,
  `conceptDesc` varchar(100) NOT NULL,
  `startDate` date NOT NULL,
  `deadline` date NOT NULL,
  `pledgeGoal` double NOT NULL,
  `reward` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT 'Processing',
  `workUpdt` varchar(100) DEFAULT NULL,
  `researcherID` varchar(9) DEFAULT NULL,
  `manufactID` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`conceptID`),
  UNIQUE KEY `conceptCode_UNIQUE` (`conceptCode`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept`
--

LOCK TABLES `concept` WRITE;
/*!40000 ALTER TABLE `concept` DISABLE KEYS */;
INSERT INTO `concept` VALUES (4,'CP0000004','73F8922C500EA686AE13A851BC6B356409982ECD929FA107FB8E800317DED863','17102E1C9ECE188853292149A2CC0DABC3A7713FF949550BDBE242D718AA9BFF','2021-03-10','2022-01-01',100000,'A free thinking egg','Processing','To be started','RS0000005','MN0000001'),(5,'CP0000005','3DD15ECA38921C553F8F549D8F457E23C616CA458D43135F71AC720D94B0BA53','5685F258DDE454389E63DE9C02EA96BD8E85DDBC2C1CBB1B92F64C3B8CEA875E','2020-12-10','2021-07-23',90000,'A free small sized nimble set','Processing','Provided to the manufacturer','RS0000003','MN0000002'),(8,'CP0000008','E0E53D1316082AF63B007CBA43AF0742D743B68550B4C039B6A1F656E7428862','8919CD2D56559041E1036FDD2EA7C71664B46D1D77081999333D63BB155A7360','2020-07-19','2021-05-05',50000,'A free set','Completed','Provided to the manufactuer','RS0000005','MN0000003'),(14,'CP0000014','E95A57884F50CF96B59B18E43264A5839C0375E4F289F20EE31577B36CEF79D4','3186F392F9F5A13F95791D710771220DC48CA682FD3A62DD3D378658A0071319','2020-05-13','2021-05-05',68000,'Animal designed wall builders','Processing','To be started','RS0000001','MN0000002'),(15,'CP0000015','75DE9835B9FAD6A6D23E2281FA69C6814B265C7C129B96180D20A1716317B77A','036236892BEB7E86E32FCC7E99DD53C720E9B390D194560D9A4226B769892C3A','2020-05-13','2021-05-05',50000,'A free polisher','Processing','To be started','RS0000001','MN0000002'),(16,'CP0000016','CE8C1A9AD3AA91DAC580973AD12FEC504DBD9A3E24E930F8EAE6E8E449DB9ADA','FCBEA43BAD9815C1BA545ED88661B357D9DE10479B4F91457D0DC976A75FFA26','2020-04-28','2021-01-01',79000,'A free set of plates from culinary','Declined','To be started','RS0000004','MN0000004'),(17,'CP0000017','41A2FF5D8B61A1EA4244D3F8817327FAC0F6A77FA24211A0A9CBF4CF97473CB6','B556827D92AF1134A41F2B82870597C15E06C0161C1E5B7C032B3BC64A1EC23D','2021-06-11','2021-12-31',89000,'Free magic lens','Processing','To be started','RS0000004','MN0000004'),(19,'CP0000019','1DBD4446E01D2CE7FE5DA49E3FDDC2679B806493A128160163DB08E2B86A5177','5F37A6F21360355CC5872F45D82FBEF91362FA6FF572307A78BF524CE00AEC04','2021-04-14','2021-12-31',50000,'A prisoner figure ','Processing','Prototype created','RS0000001','MN0000004');
/*!40000 ALTER TABLE `concept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hconceptdesc`
--

DROP TABLE IF EXISTS `hconceptdesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hconceptdesc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nKey` varchar(100) DEFAULT NULL,
  `Value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hconceptdesc`
--

LOCK TABLES `hconceptdesc` WRITE;
/*!40000 ALTER TABLE `hconceptdesc` DISABLE KEYS */;
INSERT INTO `hconceptdesc` VALUES (1,'Designed to help kids sharpen the brain','6484E4CA61715631CC70FDEF95EA16E652D70A316A80F3AB5821E51CD22B8828'),(2,'Designed to help kids sharpen the brain','6484E4CA61715631CC70FDEF95EA16E652D70A316A80F3AB5821E51CD22B8828'),(3,'ABCD','3D3688459470DD4A53866100615FEE28F311D31511912F452B4CF8277BFF6EA9'),(4,'A uniquely designed heater','A56510A100491C316C7F8EBC8F32903155C79252F6FAF5D8C1899611A8D03706'),(5,'A powerful mind rescending tool','17102E1C9ECE188853292149A2CC0DABC3A7713FF949550BDBE242D718AA9BFF'),(6,'A tool to get your nails painted perfectly','D9266FC0031A99EF4A3FDF35D38120E44122DDFFAAE917EAEED16BB72E8B7DB3'),(7,'An activity for kids to sharpen the brain','8919CD2D56559041E1036FDD2EA7C71664B46D1D77081999333D63BB155A7360'),(8,'SLim and sleek lens for eye cover','B35D040D22BE75695C77D1F82B708A9B12CDBB2B12F1FC9B5A5394AD54DCBEE2'),(9,'SLim and sleek lens for eye cover','B35D040D22BE75695C77D1F82B708A9B12CDBB2B12F1FC9B5A5394AD54DCBEE2'),(10,'Place your polish and get your nails painted','93762D296EECF6D76271AF728C5D954FF62125ECAB4D48591BD1735A59CDF3E0'),(11,'A tool to develop the brain power of kids','185CF95F2F0190C80F412013F63747F6B463E921351759E753B569532E961897'),(12,'Place your nail polish and get your nails painted perfectly','5685F258DDE454389E63DE9C02EA96BD8E85DDBC2C1CBB1B92F64C3B8CEA875E'),(13,'A tool to develop the brain power of kids','185CF95F2F0190C80F412013F63747F6B463E921351759E753B569532E961897'),(14,'Portable lamp stickers','8393A580E28F8FF8A2CFCC835FFD622B93FB1B055B4AB21EF0A6D776BF1DB933'),(15,'Sticker portable lamps','3186F392F9F5A13F95791D710771220DC48CA682FD3A62DD3D378658A0071319'),(16,'Antique designed cooler','D38065396C5721E33D7BB65ADB4C907D4E19B41C5F337974C8F9DC91D7C7A256'),(17,'Tool to auto polish nail','631194AE2AB0BDBF29350472DD30AAD06322D8078EFD365CF95DFE8DE5A7D429'),(18,'Polisher nail tool','B26EC67F6BAB185C38B1F34F00C166FC355A1024EBEF5BC2AF7D6646C820DD72'),(19,'Small and antique culinary tools','FCBEA43BAD9815C1BA545ED88661B357D9DE10479B4F91457D0DC976A75FFA26'),(20,'Simple auto nail painter tool','1C525D034240B95E69F9C43DC27131A26948A4B27CFF08D965980EF98E012638'),(21,'Eye lens that can adjust opacity ','B556827D92AF1134A41F2B82870597C15E06C0161C1E5B7C032B3BC64A1EC23D'),(23,'Retro style animal figure ','5F37A6F21360355CC5872F45D82FBEF91362FA6FF572307A78BF524CE00AEC04'),(24,'A tool to place your nail polish and paint your nails clean','036236892BEB7E86E32FCC7E99DD53C720E9B390D194560D9A4226B769892C3A');
/*!40000 ALTER TABLE `hconceptdesc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hconceptname`
--

DROP TABLE IF EXISTS `hconceptname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hconceptname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nKey` varchar(45) DEFAULT NULL,
  `Value` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hconceptname`
--

LOCK TABLES `hconceptname` WRITE;
/*!40000 ALTER TABLE `hconceptname` DISABLE KEYS */;
INSERT INTO `hconceptname` VALUES (5,'Thinking egg','73F8922C500EA686AE13A851BC6B356409982ECD929FA107FB8E800317DED863'),(7,'Jiggles Puzzle','E0E53D1316082AF63B007CBA43AF0742D743B68550B4C039B6A1F656E7428862'),(12,'Nimble nail polisher','3DD15ECA38921C553F8F549D8F457E23C616CA458D43135F71AC720D94B0BA53'),(15,'Wall builder Lamp','E95A57884F50CF96B59B18E43264A5839C0375E4F289F20EE31577B36CEF79D4'),(16,'Wall cooler','998AA52C090B52D9544888A451BC535712853B41385EDED512620D7BA436E255'),(17,'Polisher','F54A5CC2CBF4E860FD6351891A66E508EAAB3BEB480B734C891F4440B8B756AF'),(18,'Ploisher tool','B4C983F978F4A73B4BD6EF55401B96E67D063FE072E3A69E322DF15ADD2F0862'),(19,'Culinariez','CE8C1A9AD3AA91DAC580973AD12FEC504DBD9A3E24E930F8EAE6E8E449DB9ADA'),(20,'Polisher tool','C88C3E0BC2C1094FF01AD3642654ACD0DBBCA159777E6B1BDED49487599C611B'),(21,'Magic lens','41A2FF5D8B61A1EA4244D3F8817327FAC0F6A77FA24211A0A9CBF4CF97473CB6'),(23,'The prisoner','1DBD4446E01D2CE7FE5DA49E3FDDC2679B806493A128160163DB08E2B86A5177'),(24,'Au-Painter','75DE9835B9FAD6A6D23E2281FA69C6814B265C7C129B96180D20A1716317B77A');
/*!40000 ALTER TABLE `hconceptname` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'campaign_service'
--
/*!50003 DROP FUNCTION IF EXISTS `funcGetAmount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `funcGetAmount`(conID varchar(9)) RETURNS double
    DETERMINISTIC
BEGIN
	
    declare pledgeAmount double;
    
    select sum(b.pledgedAmnt) into pledgeAmount
	from campaign_service.backs b
	where b.conceptID = conID;
	
RETURN pledgeAmount;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getCustomID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getCustomID`() RETURNS varchar(9) CHARSET utf8mb4
    DETERMINISTIC
BEGIN

	declare maxID int;
    declare genID varchar(9);
    declare stringID varchar(7);
    
    select max(conceptID) into maxID from campaign_service.concept;
    
	set stringID := convert(maxID+1,char(7));
	set stringID := lpad(stringID,7,0);
	set genID := concat('CP',stringID);

    
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

-- Dump completed on 2021-04-25 19:06:34
