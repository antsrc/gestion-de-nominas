-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.39 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para gestion_de_nominas
CREATE DATABASE IF NOT EXISTS `gestion_de_nominas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gestion_de_nominas`;

-- Volcando estructura para tabla gestion_de_nominas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `nombre` varchar(50) DEFAULT NULL,
  `dni` varchar(50) NOT NULL,
  `sexo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `categoria` int DEFAULT '1',
  `antiguedad` int DEFAULT '0',
  PRIMARY KEY (`dni`),
  CONSTRAINT `empleados_chk_1` CHECK ((`sexo` in (_utf8mb4'm',_utf8mb4'f'))),
  CONSTRAINT `empleados_chk_2` CHECK ((`categoria` between 1 and 10)),
  CONSTRAINT `empleados_chk_3` CHECK ((`antiguedad` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla gestion_de_nominas.empleados: ~3 rows (aproximadamente)
INSERT INTO `empleados` (`nombre`, `dni`, `sexo`, `categoria`, `antiguedad`) VALUES
	('Ada Lovelace', '32000031R', 'F', 1, 0),
	('James Gosling', '32000032G', 'M', 4, 7),
	('Antonio', '423424224L', 'M', 1, 1);

-- Volcando estructura para tabla gestion_de_nominas.nominas
CREATE TABLE IF NOT EXISTS `nominas` (
  `dni` varchar(50) DEFAULT NULL,
  `sueldo` int DEFAULT NULL,
  KEY `fk_nominas_empleados` (`dni`),
  CONSTRAINT `fk_nominas_empleados` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`) ON DELETE CASCADE,
  CONSTRAINT `nominas_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla gestion_de_nominas.nominas: ~3 rows (aproximadamente)
INSERT INTO `nominas` (`dni`, `sueldo`) VALUES
	('32000032G', 145000),
	('32000031R', 50000),
	('423424224L', 55000);

-- Volcando estructura para disparador gestion_de_nominas.empleados_after_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `empleados_after_update` AFTER UPDATE ON `empleados` FOR EACH ROW BEGIN

   DECLARE sueldo_calculado INT;

   SET sueldo_calculado = 30000 + new.categoria * 20000 + new.antiguedad * 5000;

   UPDATE NOMINAS
   SET sueldo = sueldo_calculado
   WHERE dni = NEW.dni;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador gestion_de_nominas.nominas_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `nominas_after_insert` AFTER INSERT ON `empleados` FOR EACH ROW BEGIN

   DECLARE sueldo_calculado INT;

   SET sueldo_calculado = 30000 + new.categoria * 20000 + new.antiguedad * 5000;

   INSERT INTO NOMINAS (dni, sueldo)
   VALUES (new.dni, sueldo_calculado);

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
