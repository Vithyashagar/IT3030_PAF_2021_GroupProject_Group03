-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: paymentservice
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `gb_payments`
--

DROP TABLE IF EXISTS `gb_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gb_payments` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `paymentCode` varchar(9) DEFAULT NULL,
  `PaymentType` varchar(20) DEFAULT NULL,
  `bank` varchar(15) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `cardNo` varchar(65) DEFAULT NULL,
  `NameOnCard` varchar(65) DEFAULT NULL,
  `cvv` varchar(65) DEFAULT NULL,
  `Buyerpayment` double DEFAULT NULL,
  `ProductID` varchar(9) DEFAULT NULL,
  `ConsumerID` varchar(9) DEFAULT NULL,
  `ConceptID` varchar(9) DEFAULT NULL,
  `cardExpMonth` varchar(20) DEFAULT NULL,
  `cardExpYear` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PaymentID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gb_payments`
--

LOCK TABLES `gb_payments` WRITE;
/*!40000 ALTER TABLE `gb_payments` DISABLE KEYS */;
INSERT INTO `gb_payments` (`PaymentID`, `paymentCode`, `PaymentType`, `bank`, `paymentDate`, `cardNo`, `NameOnCard`, `cvv`, `Buyerpayment`, `ProductID`, `ConsumerID`, `ConceptID`, `cardExpMonth`, `cardExpYear`) VALUES (1,'PM0000001','Credit','BOC','2021-04-23','01F4968FC950779130EBF64CF9843AFA4FA2A5C8C1222D63E84722B5DE123C92','Jill','DAABE4A0CE7C4F0B56AA0A0DE4B1E517B2312B9EB1E3A0732C5E9A15BFB8EE27',0,'NA','CM0000004','CP0000003','5','2027'),(2,'PM0000002','credit','Sampath','2021-04-23','C6E9519B73A33BE8628AA9D1991189D4304C55A7160CD229F640CB98F37C26AA','Jack','8331668DA27B7C56857426406B415F20F9D911E64DDDF8FB8754D154F853E4E6',0,'NA','CM0000008','CP0000003','2','2025'),(39,'PM0000003','Debit','HSBC','2021-04-23','6B0EF3D06D544612390227FB8F2AA9C0A87FFACBFE9A9745714FF9DA267A37D3','John','494A6051BA7805C9B289F2377AADD3E9FD18EF4B43E82476852D57AAA80E846C',0,'NA','CM0000008','CP0000003','5','2024'),(40,'PM0000040','Debit','Commercial','2021-04-23','37DC3202FE6EEC2D65C5269E43C5608C62562B6F24981BAD8DDF109E6ED69C56','John','C9E5B08AFE21EEC7AAFE777B6EB3DEBFC313B7DFBBB9009472D8F1DE8F67E6E1',10000,'PD0000004','CM0000003','NA','3','2026');
/*!40000 ALTER TABLE `gb_payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hcardno`
--

DROP TABLE IF EXISTS `hcardno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hcardno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nKey` char(16) DEFAULT NULL,
  `nvalue` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hcardno`
--

LOCK TABLES `hcardno` WRITE;
/*!40000 ALTER TABLE `hcardno` DISABLE KEYS */;
INSERT INTO `hcardno` (`id`, `nKey`, `nvalue`) VALUES (13,'4578653478675111','01F4968FC950779130EBF64CF9843AFA4FA2A5C8C1222D63E84722B5DE123C92'),(14,'5627656787656765','44BE28F1CC8C8F67799B9C4C7CED64B807F678D1DE27899D8547480851525A91'),(15,'3476563456765679','6B0EF3D06D544612390227FB8F2AA9C0A87FFACBFE9A9745714FF9DA267A37D3'),(16,'2678765678765001','37DC3202FE6EEC2D65C5269E43C5608C62562B6F24981BAD8DDF109E6ED69C56'),(17,'5624567876567888','C6E9519B73A33BE8628AA9D1991189D4304C55A7160CD229F640CB98F37C26AA');
/*!40000 ALTER TABLE `hcardno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hcvv`
--

DROP TABLE IF EXISTS `hcvv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hcvv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nKey` char(3) DEFAULT NULL,
  `nvalue` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hcvv`
--

LOCK TABLES `hcvv` WRITE;
/*!40000 ALTER TABLE `hcvv` DISABLE KEYS */;
INSERT INTO `hcvv` (`id`, `nKey`, `nvalue`) VALUES (13,'111','DAABE4A0CE7C4F0B56AA0A0DE4B1E517B2312B9EB1E3A0732C5E9A15BFB8EE27'),(14,'765','822422D4591DA10BA3FC5CA738DDC9AC2DC4C330884BF9C25374418024C614D6'),(15,'679','494A6051BA7805C9B289F2377AADD3E9FD18EF4B43E82476852D57AAA80E846C'),(16,'001','C9E5B08AFE21EEC7AAFE777B6EB3DEBFC313B7DFBBB9009472D8F1DE8F67E6E1'),(17,'888','8331668DA27B7C56857426406B415F20F9D911E64DDDF8FB8754D154F853E4E6');
/*!40000 ALTER TABLE `hcvv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'paymentservice'
--
/*!50003 DROP FUNCTION IF EXISTS `getPaymentID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getPaymentID`() RETURNS varchar(9) CHARSET utf8mb4
    DETERMINISTIC
BEGIN

    declare maxID int;
    declare genID varchar(9);
    declare stringID varchar(7);
    
    select max(PaymentID) into maxID from paymentservice.gb_payments;
    
    set stringID := convert(maxID+1,char(7));
    set stringID := lpad(stringID,7,0);
    set genID := concat('PM',stringID);

 

    RETURN genID;

 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `insertProductAmount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `insertProductAmount`(productID varchar(9) , consumerID varchar(9)) RETURNS double
    DETERMINISTIC
BEGIN

    declare totalProductCost double;
	declare productqty double;
	declare productPrice double;
    declare maxPayID int;
   
	   select b.qty into productqty
	   from productservice.buying b
	   where b.productID=productID AND b.consumerID = consumerID;
			
	   select p.productPrice into productPrice
	   from productservice.product p
	   where p.productCode=productID ;
       	
	   
	   set totalProductCost = productqty * productPrice ;
       
        return totalProductCost;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateStatus`(conceptID varchar(9))
BEGIN
	/*Declaring variables to hold campaign values*/
		declare totalPledgeAmount double;
        declare pledgeGoal double;
        declare pledgeDeadline datetime;
        declare trackingDate datetime;
		SET trackingDate = now(); /*Getting presentDate*/
        
        select sum(b.pledgedAmnt) into totalPledgeAmount
        from concept_service.backs b
        where b.conceptID = conceptID;/*Summing collected pledgeAmt for given campaignID*/
        
        select c.pledgeGoal into pledgeGoal
        from concept_service.concept c
        where c.conceptCode = conceptID;/*Obtaining actual goal amount of researcher*/
        
         select c.deadline into pledgeDeadline
        from concept_service.concept c
        where c.conceptCode = conceptID;/*Obtaining actual deadline set by researcher*/
       	
        /*Tracking deadline and pledge amount to update status*/
       IF(trackingDate < pledgeDeadline) THEN
         IF(pledgeGoal = totalPledgeAmount)THEN
        BEGIN
            update concept_service.concept c
            set c.status = "Completed"
            where c.conceptCode = conceptID;
        END;
        
        elseif(pledgeGoal > totalPledgeAmount) THEN
        BEGIN
            update concept_service.concept c
            set c.status = "Processing"
            where c.conceptCode = conceptID;
         END;   
		
        END IF;/*end of first IF*/
        
        elseif(trackingDate > pledgeDeadline)THEN
        IF(pledgeGoal = totalPledgeAmount)THEN
        BEGIN
            update concept_service.concept c
            set c.status = "Completed"
            where c.conceptCode = conceptID;
        END;

        elseif(pledgeGoal > totalPledgeAmount) THEN
        BEGIN
            update concept_service.concept c
            set c.status = "Declined"
            where c.conceptCode = conceptID;
         END;   
        END IF;
        END IF;
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

-- Dump completed on 2021-04-25 17:39:26
