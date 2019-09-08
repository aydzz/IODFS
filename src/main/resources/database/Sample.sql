/*
SQLyog Community v13.1.1 (64 bit)
MySQL - 8.0.13 : Database - iods
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iods` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `iods`;

/*Table structure for table `document` */

DROP TABLE IF EXISTS `document`;

CREATE TABLE `document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_office` varchar(255) DEFAULT NULL,
  `date_received` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `doctype` varchar(255) DEFAULT NULL,
  `forwarded_office` varchar(255) DEFAULT NULL,
  `source_office` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trackingnum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `document` */

insert  into `document`(`id`,`current_office`,`date_received`,`description`,`doctype`,`forwarded_office`,`source_office`,`status`,`trackingnum`) values 
(17,'Office of the Dean','September 07, 2019','asd','Ace Form',NULL,'CPE Department','PENDING','2019-9-7-AF-035'),
(18,'CPE Department','September 07, 2019','asdasdasdsadas','Ace Form',NULL,'User Office','PENDING','2019-9-7-AF-036'),
(19,'CPE Department','September 07, 2019','aasdsadadsdaadssda','Ace Form',NULL,'User Office','PENDING','2019-9-7-AF-037'),
(20,'CPE Department','September 08, 2019','asdasdsadsasadsadsadsadsa','Ace Form',NULL,'Office of the Dean','PENDING','2019-9-8-AF-038'),
(21,'Office of the Dean','September 08, 2019','FROM USER OFFICE','Ace Form',NULL,'User Office','PENDING','2019-9-8-AF-039'),
(22,NULL,'September 08, 2019','Description After RELEASE!','Completion Form',NULL,'User Office','RELEASED:COMPLETED','2019-9-8-CF-013');

/*Table structure for table `office` */

DROP TABLE IF EXISTS `office`;

CREATE TABLE `office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `officecode` varchar(255) DEFAULT NULL,
  `officename` varchar(255) DEFAULT NULL,
  `officetype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `office` */

insert  into `office`(`id`,`officecode`,`officename`,`officetype`) values 
(1,'do','Office of the Dean','office'),
(2,'cpedept','CPE Department','department'),
(3,'vpaa','Office of the VPAA','office'),
(4,'registrar','Office of the Registrar','office');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `office` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`username`,`office`,`email`) values 
(5,'ADMIN','admin','Admin Office','admin@admin'),
(6,'ADMINISTRATOR','administrator','Admin Office','administrator@administrator'),
(7,'Julius Cansino','cpedept','CPE Department','julius@pup.edu'),
(8,'Maria Ado','deansoffice','Office of the Dean','ado@pup.edu'),
(4,'USER','user','User Office','user@user');

/*Table structure for table `user_login` */

DROP TABLE IF EXISTS `user_login`;

CREATE TABLE `user_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'USER',
  PRIMARY KEY (`id`),
  KEY `user_login_ibfk_1` (`username`),
  CONSTRAINT `user_login_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_login` */

insert  into `user_login`(`id`,`username`,`password`,`role`) values 
(5,'user','$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS','USER'),
(6,'admin','$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS','admin'),
(7,'administrator','$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS','admin'),
(8,'cpedept','$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS','USER'),
(9,'deansoffice','$2a$10$rguqKi7Bnq416OIcZCJuA.YafJxUHNdJcBDX7fuQhiSsZ5UVlA/NS','USER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
