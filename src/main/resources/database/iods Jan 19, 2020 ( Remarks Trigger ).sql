-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 19, 2020 at 02:15 PM
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
(1, 16, 'AF'),
(2, 3, 'MOA'),
(3, 4, 'PETITION'),
(4, 6, 'CF'),
(5, 1, 'R0');

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
  `trackingnum` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`id`, `current_office`, `date_received`, `description`, `doctype`, `forwarded_office`, `source_office`, `status`, `trackingnum`, `remark`) VALUES
(1, NULL, '2019-10-13', 'Ace 1', 'AF', NULL, 'Office of the Dean', 'RELEASED:COMPLETED', '2019-10-13-AF-001', ''),
(2, NULL, '2019-10-13', 'CF 001', 'CF', NULL, 'CPE Department', 'RELEASED:DEFICIENT', '2019-10-13-CF-001', ''),
(3, 'CPE Department', '2019-12-10', '', 'CF', NULL, 'CPE Department', 'PENDING', '2019-12-10-CF-002', NULL),
(8, '', '2020-01-10', 'vb net petition', 'PETITION', NULL, '', 'pending', '2020-01-10-PETITION-001', NULL),
(9, 'CPE Department', '2020-01-10', 'dasdasd', 'AF', NULL, 'CPE Department', 'pending', '2020-01-10-AF-003', NULL),
(10, 'CPE Department', '2020-01-13', 'we', 'AF', NULL, 'CPE Department', 'pending', '2020-01-13-AF-004', NULL),
(11, 'CPE Department', '2020-01-13', 'gumagana', 'AF', NULL, 'CPE Department', 'pending', '2020-01-13-AF-005', NULL),
(12, 'CPE Department', '2020-01-13', 'Naman', 'CF', NULL, 'CPE Department', 'pending', '2020-01-13-CF-003', NULL),
(13, 'CPE Department', '2020-01-13', 'Kaya', 'MOA', NULL, 'CPE Department', 'pending', '2020-01-13-MOA-001', NULL),
(14, 'CPE Department', '2020-01-13', 'nang sunod sunod', 'PETITION', NULL, 'CPE Department', 'pending', '2020-01-13-PETITION-002', NULL),
(15, 'CPE Department', '2020-01-14', 'ace 1', 'AF', NULL, 'CPE Department', 'pending', '2020-01-14-AF-006', NULL),
(16, 'CPE Department', '2020-01-14', 'ace 2', 'AF', NULL, 'CPE Department', 'pending', '2020-01-14-AF-007', NULL),
(17, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-008', NULL),
(18, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-009', NULL),
(19, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-010', NULL),
(20, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-011', NULL),
(21, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-012', NULL),
(22, 'CPE Department', '2020-01-17', '123', 'AF', NULL, 'CPE Department', 'pending', '2020-01-17-AF-013', NULL),
(23, NULL, '2020-01-19', 'asdas123', 'CF', NULL, 'Office of the Dean', 'RELEASED:COMPLETED', '2020-01-19-CF-004', 'bebe'),
(24, 'CPE Department', '2020-01-19', '123123123123', 'AF', 'Office of the Dean', 'CPE Department', 'FORWARDED', '2020-01-19-AF-014', 'hakdug'),
(25, 'CPE Department', '2020-01-19', 'pet', 'PETITION', NULL, 'CPE Department', 'PENDING', '2020-01-19-PETITION-003', NULL),
(26, 'CPE Department', '2020-01-19', '323232323', 'AF', NULL, 'CPE Department', 'PENDING', '2020-01-19-AF-015', NULL),
(27, 'CPE Department', '2020-01-19', '32323', 'MOA', NULL, 'CPE Department', 'PENDING', '2020-01-19-MOA-002', NULL),
(28, 'CPE Department', '2020-01-19', 'ninasd', 'CF', NULL, 'CPE Department', 'PENDING', '2020-01-19-CF-005', NULL);

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
	INSERT INTO `user_logs`(`cts`,`action`,`remark`,`datetime`) VALUES(NEW.trackingnum,CONCAT('FORWARDED by ', OLD.current_office, ' to ',NEW.forwarded_office),NEW.remark,NOW());
    
ELSEIF NEW.status LIKE 'PENDING' THEN
	INSERT INTO `user_logs`(`cts`,`action`,`remark`,`datetime`) VALUES(NEW.trackingnum,CONCAT('RECEIVED by ', NEW.current_office, ' from ',OLD.current_office),NEW.remark,NOW());
ELSE
    INSERT INTO `user_logs`(`cts`,`action`,`remark`,`datetime`) VALUES(NEW.trackingnum,CONCAT('RELEASED by ', OLD.current_office),NEW.remark,NOW());
     INSERT INTO `history`(`Date`,`cts`,`office`,`doctype`,`status`,`remark`) VALUES(OLD.date_received,OLD.trackingnum,OLD.current_office,OLD.doctype,NEW.status,NEW.remark);
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
  `status` varchar(45) DEFAULT NULL,
  `remark` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`id`, `Date`, `cts`, `office`, `doctype`, `status`, `remark`) VALUES
(1, '2019-10-13', '2019-10-13-CF-001', 'CPE Department', 'CF', 'RELEASED:DEFICIENT', ''),
(2, '2019-10-13', '2019-10-13-AF-001', 'CPE Department', 'AF', 'RELEASED:COMPLETED', ''),
(3, '2020-01-19', '2020-01-19-CF-004', 'CPE Department', 'CF', 'RELEASED:COMPLETED', 'bebe');

-- --------------------------------------------------------

--
-- Table structure for table `office`
--

CREATE TABLE `office` (
  `id` bigint(20) NOT NULL,
  `officecode` varchar(255) DEFAULT NULL,
  `officename` varchar(255) DEFAULT NULL,
  `officetype` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `office`
--

INSERT INTO `office` (`id`, `officecode`, `officename`, `officetype`) VALUES
(1, 'Office of the Dean', 'Dean\'s Offices', NULL),
(2, 'CPE Department', 'Computering Engineering Departments', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `id` bigint(20) NOT NULL,
  `doc_abbrev` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `doc_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `doc_abbrev`, `doc_type`) VALUES
(1, 'AF', 'Ace Form'),
(2, 'CF', 'Completion Form'),
(3, 'MOA', 'Memorandum of Agreement'),
(4, 'PETITION', 'PETITION');

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
(4, 'Remedios Ado', 'CoEDean', 'Office of the Dean', 'CoEDean@pup.edu.ph', NULL, 'CoE Dean'),
(3, 'Julius Cansino', 'CPEDept', 'CPE Department', 'CPEDept@pup.edu.ph', NULL, 'CPE Department'),
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
  `remark` varchar(400) NOT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_logs`
--

INSERT INTO `user_logs` (`id`, `cts`, `office`, `action`, `remark`, `destination`, `datetime`) VALUES
(1, '2019-10-13-AF-001', NULL, 'ADDED by CPE Department', '', NULL, '2019-10-13 21:30:08'),
(2, '2019-10-13-CF-001', NULL, 'ADDED by CPE Department', '', NULL, '2019-10-13 21:30:33'),
(3, '2019-10-13-AF-001', NULL, 'FORWARDED by CPE Department to Office of the Dean', '', NULL, '2019-10-13 21:31:38'),
(4, '2019-10-13-CF-001', NULL, 'RELEASED by CPE Department', '', NULL, '2019-10-13 21:32:05'),
(5, '2019-10-13-AF-001', NULL, 'RECEIVED by Office of the Dean from CPE Department', '', NULL, '2019-10-13 21:32:24'),
(6, '2019-10-13-AF-001', NULL, 'FORWARDED by Office of the Dean to CPE Department', '', NULL, '2019-10-13 21:32:44'),
(7, '2019-10-13-AF-001', NULL, 'RECEIVED by CPE Department from Office of the Dean', '', NULL, '2019-10-13 21:33:03'),
(8, '2019-10-13-AF-001', NULL, 'RELEASED by CPE Department', '', NULL, '2019-10-13 21:33:14'),
(9, '2019-12-10-CF-002', NULL, 'ADDED by CPE Department', '', NULL, '2019-12-10 21:37:09'),
(10, NULL, NULL, 'ADDED by CPE Department', '', NULL, '2020-01-10 23:10:33'),
(11, NULL, NULL, 'ADDED by ', '', NULL, '2020-01-10 23:11:15'),
(12, NULL, NULL, 'ADDED by CPE Department', '', NULL, '2020-01-10 23:11:57'),
(13, '2020-01-10-AF-002', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-10 23:13:15'),
(14, '2020-01-10-PETITION-001', NULL, 'ADDED by ', '', NULL, '2020-01-10 23:13:48'),
(15, '2020-01-10-AF-003', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-10 23:14:57'),
(16, '2020-01-13-AF-004', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-13 12:26:42'),
(17, '2020-01-13-AF-005', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-13 12:27:26'),
(18, '2020-01-13-CF-003', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-13 12:27:33'),
(19, '2020-01-13-MOA-001', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-13 12:27:39'),
(20, '2020-01-13-PETITION-002', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-13 12:27:46'),
(21, '2020-01-14-AF-006', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-14 11:06:11'),
(22, '2020-01-14-AF-007', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-14 11:06:15'),
(23, '2020-01-17-AF-008', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:09:21'),
(24, '2020-01-17-AF-009', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:12:06'),
(25, '2020-01-17-AF-010', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:12:26'),
(26, '2020-01-17-AF-011', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:12:39'),
(27, '2020-01-17-AF-012', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:12:52'),
(28, '2020-01-17-AF-013', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-17 15:13:39'),
(29, '2020-01-19-CF-004', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 19:46:19'),
(30, '2020-01-19-CF-004', NULL, 'FORWARDED by CPE Department to Office of the Dean', '', NULL, '2020-01-19 20:58:10'),
(31, '2020-01-19-CF-004', NULL, 'RECEIVED by Office of the Dean from CPE Department', '', NULL, '2020-01-19 21:00:40'),
(32, '2020-01-19-CF-004', NULL, 'FORWARDED by Office of the Dean to CPE Department', '', NULL, '2020-01-19 21:02:36'),
(33, '2020-01-19-AF-014', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 21:03:02'),
(34, '2020-01-19-CF-004', NULL, 'RECEIVED by CPE Department from Office of the Dean', '', NULL, '2020-01-19 21:04:10'),
(35, '2020-01-19-CF-004', NULL, 'RELEASED by CPE Department', '', NULL, '2020-01-19 21:04:33'),
(36, '2020-01-19-PETITION-003', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 21:06:35'),
(37, '2020-01-19-AF-015', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 21:11:35'),
(38, '2020-01-19-MOA-002', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 21:12:54'),
(39, '2020-01-19-CF-005', NULL, 'ADDED by CPE Department', '', NULL, '2020-01-19 21:14:18'),
(40, '2020-01-19-AF-014', NULL, 'FORWARDED by CPE Department to Office of the Dean', 'hakdug', NULL, '2020-01-19 21:14:44');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `counter`
--
ALTER TABLE `counter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `office`
--
ALTER TABLE `office`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`),
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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `office`
--
ALTER TABLE `office`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `resetctr` ON SCHEDULE EVERY 1 DAY STARTS '2020-01-14 14:06:54' ON COMPLETION PRESERVE ENABLE DO UPDATE `counter` SET `ctr` = 3$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
