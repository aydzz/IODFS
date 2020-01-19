-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2019 at 04:16 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iods`
--

-- --------------------------------------------------------

--
-- Table structure for table `counter`
--

CREATE TABLE `counter` (
  `id` bigint(20) NOT NULL,
  `ctr` int(11) NOT NULL,
  `doc_abbrev` varchar(255) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `counter`
--

INSERT INTO `counter` (`id`, `ctr`, `doc_abbrev`) VALUES
(1, 4, 'AF'),
(2, 1, 'MOA'),
(3, 1, 'PETITION'),
(4, 2, 'CF'),
(5, 1, 'R-0'),
(6, 1, 'Tutorial'),
(7, 1, 'AF23'),
(8, 1, 'AF23');

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

CREATE TABLE `document` (
  `id` bigint(20) NOT NULL,
  `current_office` varchar(255) DEFAULT NULL,
  `date_received` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `doctype` varchar(255) DEFAULT NULL,
  `forwarded_office` varchar(255) DEFAULT NULL,
  `source_office` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trackingnum` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`id`, `current_office`, `date_received`, `description`, `doctype`, `forwarded_office`, `source_office`, `status`, `trackingnum`, `remark`) VALUES
(1, 'CPE Department', '2019-10-22', 'ACE FORM', 'AF', 'CoE Dean', 'CPE Department', 'FORWARDED', '2019-10-22-AF-001', 'Hi'),
(2, 'Office of the Dean', '2019-10-22', 'Completion', 'CF', NULL, 'CPE Department', 'PENDING', '2019-10-22-CF-001', 'asd'),
(3, 'CPE Department', '2019-10-22', 'Select * From Table 1 Where \' \'', 'AF', 'CoE Dean', 'CPE Department', 'FORWARDED', '2019-10-22-AF-002', '\'\' Select * From Table 1 '),
(4, 'Office of the Dean', '2019-10-22', '1st', 'AF', 'CoE Dean', 'Office of the Dean', 'FORWARDED', '2019-10-22-AF-003', 'remarks');

--
-- Triggers `document`
--
DELIMITER $$
CREATE TRIGGER `afctruserlogs` AFTER INSERT ON `document` FOR EACH ROW BEGIN

	 UPDATE `counter` SET `ctr` = `ctr` + 1 WHERE `doc_abbrev`=NEW.doctype;
	 INSERT INTO `user_logs`(`cts`,`action`,`datetime`) VALUES(NEW.trackingnum,CONCAT('ADDED by ' ,NEW.source_office),NOW());
	
	
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `ctruserlogs` BEFORE INSERT ON `document` FOR EACH ROW BEGIN
IF ((SELECT `ctr` from `counter` WHERE `doc_abbrev`=NEW.doctype)<10) THEN
SET NEW.trackingnum = CONCAT(NEW.date_received, '-',(SELECT `doc_abbrev` from `counter` where `doc_abbrev`=NEW.doctype),'-00',(SELECT `ctr` from `counter` where `doc_abbrev`=NEW.doctype));
ELSEIF ((SELECT `ctr` from `counter` WHERE `doc_abbrev`=NEW.doctype)>=10 AND (SELECT `ctr` from `counter` WHERE `doc_abbrev`=NEW.doctype)<99) THEN
SET NEW.trackingnum = CONCAT(NEW.date_received, '-',(SELECT `doc_abbrev` from `counter` where `doc_abbrev`=NEW.doctype),'-0',(SELECT `ctr` from `counter` where `doc_abbrev`=NEW.doctype));
ELSE 
SET NEW.trackingnum = CONCAT(NEW.date_received, '-',(SELECT `doc_abbrev` from `counter` where `doc_abbrev`=NEW.doctype),'-',(SELECT `ctr` from `counter` where `doc_abbrev`=NEW.doctype));
END IF;			
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `userlogs` AFTER UPDATE ON `document` FOR EACH ROW BEGIN

IF NEW.status LIKE 'FORWARDED' THEN
	INSERT INTO `user_logs`(`cts`,`action`,`datetime`) VALUES(NEW.trackingnum,CONCAT('FORWARDED by ', OLD.current_office, ' to ',NEW.forwarded_office),NOW());
    
ELSEIF NEW.status LIKE 'PENDING' THEN
	INSERT INTO `user_logs`(`cts`,`action`,`datetime`) VALUES(NEW.trackingnum,CONCAT('RECEIVED by ', NEW.current_office, ' from ',OLD.current_office),NOW());
ELSE
    INSERT INTO `user_logs`(`cts`,`action`,`datetime`) VALUES(NEW.trackingnum,CONCAT('RELEASED by ', OLD.current_office),NOW());
     INSERT INTO `history`(`Date`,`cts`,`office`,`doctype`,`status`) VALUES(OLD.date_received,OLD.trackingnum,OLD.current_office,OLD.doctype,NEW.status);
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `id` bigint(20) NOT NULL,
  `Date` varchar(255) DEFAULT NULL,
  `cts` varchar(45) DEFAULT NULL,
  `office` varchar(45) DEFAULT NULL,
  `doctype` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`id`, `Date`, `cts`, `office`, `doctype`, `status`) VALUES
(1, '2019-10-13', '2019-10-13-CF-001', 'CPE Department', 'CF', 'RELEASED:DEFICIENT'),
(2, '2019-10-13', '2019-10-13-AF-001', 'CPE Department', 'AF', 'RELEASED:COMPLETED');

-- --------------------------------------------------------

--
-- Table structure for table `office`
--

CREATE TABLE `office` (
  `id` bigint(20) NOT NULL,
  `officecode` varchar(255) DEFAULT NULL,
  `officename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `office`
--

INSERT INTO `office` (`id`, `officecode`, `officename`) VALUES
(1, 'CPE Department', 'Computer Engineering Department'),
(2, 'Office of the Dean', 'Office of the Dean lol'),
(3, 'VPAA', 'Office of the VPAA'),
(5, 'Registrar', 'Office of the Registrar');

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `id` bigint(20) NOT NULL,
  `doc_abbrev` varchar(255) CHARACTER SET utf8 NOT NULL,
  `doc_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `doc_abbrev`, `doc_type`) VALUES
(1, 'AF', 'Ace Form'),
(7, 'AF23', 'Ace Form 1234'),
(8, 'AF23', 'Ace Form 1234dddddd'),
(2, 'CF', 'Completion Form'),
(3, 'MOA', 'Memorandum of Agreement'),
(4, 'PETITION', 'PETITION'),
(6, 'R-0', 'R-0 Form'),
(5, 'Tutorial', 'Tutorial');

--
-- Triggers `type`
--
DELIMITER $$
CREATE TRIGGER `type_AFTER_INSERT` AFTER INSERT ON `type` FOR EACH ROW BEGIN
	INSERT INTO `counter` (`ctr`,`doc_abbrev`) VALUES (1,NEW.doc_abbrev);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `office` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `office`, `email`, `lname`, `fullname`) VALUES
(1, 'ADMIN', 'admin', 'Admin Office', 'admin@pup.edu.ph', 'Admin', 'Administrator'),
(2, 'ADMINISTRATOR', 'administrator', 'Admin Office', 'administrator@administrator', NULL, NULL),
(8, 'che', 'che', 'CPE Department', 'che@gmail.com', 'che', NULL),
(4, 'Remedios', 'CoEDean', 'Office of the Dean', 'CoEDean@pup.edu.ph', 'Ado', 'CoE Dean'),
(3, 'Julius', 'CPEDept', 'CPE Department', 'CPEDept@pup.edu.ph', 'Cansino', 'CPE Department'),
(5, 'Registrar', 'Registrar', 'Office of the Registrar', 'Registrar@pup.edu.ph', NULL, 'Registrar'),
(6, 'VPAA', 'VPAA', 'Office of the VPAA', 'VPAA@pup.edu.ph', NULL, 'VPAA');

-- --------------------------------------------------------

--
-- Table structure for table `user_login`
--

CREATE TABLE `user_login` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(10) DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_login`
--

INSERT INTO `user_login` (`id`, `username`, `password`, `role`) VALUES
(1, 'user', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'USER'),
(2, 'admin', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'admin'),
(3, 'administrator', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'admin'),
(4, 'CPEDept', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'USER'),
(5, 'CoEDean', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'OFFICE'),
(6, 'VPAA', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'OFFICE'),
(7, 'Registrar', '$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS', 'OFFICE');

-- --------------------------------------------------------

--
-- Table structure for table `user_logs`
--

CREATE TABLE `user_logs` (
  `id` bigint(20) NOT NULL,
  `cts` varchar(45) DEFAULT NULL,
  `office` varchar(45) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_logs`
--

INSERT INTO `user_logs` (`id`, `cts`, `office`, `action`, `destination`, `datetime`) VALUES
(1, '2019-10-22-AF-001', NULL, 'ADDED by CPE Department', NULL, '2019-10-22 17:50:20'),
(2, '2019-10-22-CF-001', NULL, 'ADDED by CPE Department', NULL, '2019-10-22 18:03:45'),
(3, '2019-10-22-AF-001', NULL, 'FORWARDED by CPE Department to CoE Dean', NULL, '2019-10-22 18:04:21'),
(4, '2019-10-22-AF-002', NULL, 'ADDED by CPE Department', NULL, '2019-10-22 18:10:14'),
(5, '2019-10-22-AF-002', NULL, 'FORWARDED by CPE Department to CoE Dean', NULL, '2019-10-22 18:12:06'),
(6, '2019-10-22-AF-003', NULL, 'ADDED by Office of the Dean', NULL, '2019-10-22 18:17:46'),
(7, '2019-10-22-AF-003', NULL, 'FORWARDED by Office of the Dean to CoE Dean', NULL, '2019-10-22 18:18:06'),
(8, '2019-10-22-CF-001', NULL, 'FORWARDED by CPE Department to Office of the Dean', NULL, '2019-10-23 20:18:22'),
(9, '2019-10-22-CF-001', NULL, 'RECEIVED by Office of the Dean from CPE Department', NULL, '2019-10-23 20:18:36');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `counter`
--
ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`,`doc_abbrev`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`id`,`trackingnum`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `office`
--
ALTER TABLE `office`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_lcyc62dnu4k4fsmj2ht11o6o1` (`officecode`),
  ADD UNIQUE KEY `UK_c8kkum2vksm1e866ke0wrokv7` (`officename`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`,`doc_abbrev`),
  ADD UNIQUE KEY `UK_6jixvupkv59daxbhohxxa5tlu` (`doc_type`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `user_login`
--
ALTER TABLE `user_login`
  ADD PRIMARY KEY (`id`,`username`);

--
-- Indexes for table `user_logs`
--
ALTER TABLE `user_logs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `counter`
--
ALTER TABLE `counter`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `office`
--
ALTER TABLE `office`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user_login`
--
ALTER TABLE `user_login`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user_logs`
--
ALTER TABLE `user_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `resetctr` ON SCHEDULE EVERY 1 DAY STARTS '2019-10-23 01:00:00' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE `counter` SET `ctr` = 1$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
