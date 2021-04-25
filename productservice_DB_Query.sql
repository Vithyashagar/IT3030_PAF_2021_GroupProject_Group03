-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2021 at 07:12 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `productservice`
--

DELIMITER $$
--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `getProductCode` () RETURNS VARCHAR(9) CHARSET utf8mb4 BEGIN


declare maxID int;
declare genID varchar(9);
declare stringID varchar(7);

select max(productId) into maxID from productservice.product;

set stringID := convert(maxID+1,char(7));
set stringID := lpad(stringID,7,0);
set genID := concat('PD',stringID);


RETURN genID;



END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `buying`
--

CREATE TABLE `buying` (
  `consumerID` varchar(9) NOT NULL,
  `productID` varchar(9) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buying`
--

INSERT INTO `buying` (`consumerID`, `productID`, `qty`) VALUES
('CM0000002', '2', 3),
('CM0000002', 'PD0000008', 4);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productId` int(11) NOT NULL,
  `productCode` varchar(9) NOT NULL,
  `productName` varchar(30) NOT NULL,
  `productPrice` decimal(8,2) NOT NULL,
  `productDesc` varchar(200) NOT NULL,
  `productCat` varchar(50) NOT NULL,
  `productQty` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productId`, `productCode`, `productName`, `productPrice`, `productDesc`, `productCat`, `productQty`) VALUES
(6, 'PD0000015', 'Normal gas', '1000.00', 'Not included', 'Electrical', 4),
(7, 'PD0000007', 'Organ', '3000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', 'Instrument', 2),
(8, 'PD0000008', 'Mixer', '2000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', 'Eclectronic', 2),
(9, 'PD0000009', '', '6000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', 'Eclectronic', 2),
(10, 'PD0000010', 'CobraStand', '6000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', 'Eclectronic', 2),
(11, 'PD0000011', 'CobraStand', '6000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', 'Eclectronic', 2),
(12, 'PD0000012', 'CobraStand', '6000.00', 'Retrocomputing is the use of older computer hardware and software in modern times. Retrocomputing is usually class', '', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buying`
--
ALTER TABLE `buying`
  ADD PRIMARY KEY (`consumerID`,`productID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
