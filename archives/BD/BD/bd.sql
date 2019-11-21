CREATE DATABASE  IF NOT EXISTS `sged` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sged`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: sged
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `tblgrupo`
--

DROP TABLE IF EXISTS `tblgrupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblgrupo` (
  `idGrupo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `descricao` varchar(300) DEFAULT NULL,
  `nivel` int(11) NOT NULL,
  PRIMARY KEY (`idGrupo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblgrupo`
--

LOCK TABLES `tblgrupo` WRITE;
/*!40000 ALTER TABLE `tblgrupo` DISABLE KEYS */;
INSERT INTO `tblgrupo` VALUES (1,'Criação de server TOMCAT','Criar Server TOMCAT ',2),(2,'Server TOMCAT','Criar Server TOMCAT ',2),(3,'Grupo Teste','Teste',1);
/*!40000 ALTER TABLE `tblgrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblitem`
--

DROP TABLE IF EXISTS `tblitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblitem` (
  `idItem` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(10) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `restricoes` varchar(1000) DEFAULT NULL,
  `src` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idItem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblitem`
--

LOCK TABLES `tblitem` WRITE;
/*!40000 ALTER TABLE `tblitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuario`
--

DROP TABLE IF EXISTS `tblusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(30) DEFAULT NULL,
  `nivelDeAcesso` int(11) DEFAULT NULL,
  `situacao` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuario`
--

LOCK TABLES `tblusuario` WRITE;
/*!40000 ALTER TABLE `tblusuario` DISABLE KEYS */;
INSERT INTO `tblusuario` VALUES (1,'Professor Isidro','isidro@professorisidro.com.br','1234',0,1),(2,'Caique de Camargo','caique.de.camargo@hotmail.com','1234',0,1),(3,'Giovanna Murakami de Oliveira','giovanna.murakami@gmail.com','1234',0,1),(4,'teste','teste@teste.com','1234',1,1),(13,'Teste 2','teste2@teste.com','teste',NULL,0),(14,'Teste 3','teste3@teste.com','1234',NULL,0);
/*!40000 ALTER TABLE `tblusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuariogrupo`
--

DROP TABLE IF EXISTS `tblusuariogrupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusuariogrupo` (
  `idUsuario` int(11) NOT NULL,
  `idGrupo` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idGrupo`),
  KEY `pk_gr` (`idGrupo`),
  CONSTRAINT `pk_gr` FOREIGN KEY (`idGrupo`) REFERENCES `tblgrupo` (`idGrupo`),
  CONSTRAINT `pk_us` FOREIGN KEY (`idUsuario`) REFERENCES `tblusuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuariogrupo`
--

LOCK TABLES `tblusuariogrupo` WRITE;
/*!40000 ALTER TABLE `tblusuariogrupo` DISABLE KEYS */;
INSERT INTO `tblusuariogrupo` VALUES (2,2),(2,3);
/*!40000 ALTER TABLE `tblusuariogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuarioitem`
--

DROP TABLE IF EXISTS `tblusuarioitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusuarioitem` (
  `idUsuario` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idItem`),
  KEY `fk_it` (`idItem`),
  CONSTRAINT `fk_it` FOREIGN KEY (`idItem`) REFERENCES `tblitem` (`idItem`),
  CONSTRAINT `fk_ui` FOREIGN KEY (`idUsuario`) REFERENCES `tblusuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuarioitem`
--

LOCK TABLES `tblusuarioitem` WRITE;
/*!40000 ALTER TABLE `tblusuarioitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblusuarioitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-18 13:51:27
