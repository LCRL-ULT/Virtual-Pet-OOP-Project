-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2026 at 11:34 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `virtualpet`
--

-- --------------------------------------------------------

--
-- Table structure for table `pets`
--

CREATE TABLE `pets` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `hunger` int(11) DEFAULT NULL,
  `happiness` int(11) DEFAULT NULL,
  `energy` int(11) DEFAULT NULL,
  `owner` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pets`
--

INSERT INTO `pets` (`id`, `name`, `type`, `hunger`, `happiness`, `energy`, `owner`) VALUES
(1, 'Rex', 'Dog', 50, 50, 50, 'Alex'),
(2, 'Whiskers', 'Cat', 50, 50, 50, 'Alex'),
(3, 'Smaug', 'Dragon', 50, 50, 50, 'Alex'),
(4, 'Rex', 'Dog', 50, 50, 50, 'Alex'),
(5, 'Whiskers', 'Cat', 50, 50, 50, 'Alex'),
(6, 'Smaug', 'Dragon', 50, 50, 50, 'Alex'),
(7, 'Rex', 'Dog', 50, 50, 50, 'Alex'),
(8, 'Whiskers', 'Cat', 50, 50, 50, 'Alex'),
(9, 'Smaug', 'Dragon', 50, 50, 50, 'Alex'),
(10, 'Rex', 'Dog', 50, 50, 50, 'Alex'),
(11, 'Whiskers', 'Cat', 50, 50, 50, 'Alex'),
(12, 'Smaug', 'Dragon', 50, 50, 50, 'Alex'),
(13, 'Rex', 'Dog', 50, 50, 50, 'Alex'),
(14, 'Whiskers', 'Cat', 50, 50, 50, 'Alex'),
(15, 'Smaug', 'Dragon', 50, 50, 50, 'Alex');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pets`
--
ALTER TABLE `pets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
