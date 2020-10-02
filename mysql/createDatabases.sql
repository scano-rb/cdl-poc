DROP DATABASE IF EXISTS sps433;
CREATE DATABASE sps433;
USE sps433;

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `idtransaccion` int(11) NOT NULL AUTO_INCREMENT,
  `nro_terminal` varchar(8) NOT NULL DEFAULT '',
  `idsite` varchar(8) COLLATE latin1_bin DEFAULT NULL,
  `idmediopago` smallint(3) DEFAULT NULL,
  `mcc` smallint(6) DEFAULT NULL,
  `monto` decimal(22,2) DEFAULT NULL,
  `nrotarjeta` varchar(200) COLLATE latin1_bin DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`idtransaccion`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `transaction` (`nro_terminal`, `idsite`, `idmediopago`, `mcc`, `monto`, `nrotarjeta`, `fecha`)
VALUES ('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'),('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17'), ('1234','1023', 65, 5399, 1000.00, '399943344', '2020-09-21 13:17:17');