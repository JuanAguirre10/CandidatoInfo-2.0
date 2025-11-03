CREATE DATABASE  IF NOT EXISTS `candidatoinfo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `candidatoinfo`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: candidatoinfo
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.14-MariaDB

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
-- Table structure for table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group_permissions`
--

DROP TABLE IF EXISTS `auth_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add log entry',1,'add_logentry'),(2,'Can change log entry',1,'change_logentry'),(3,'Can delete log entry',1,'delete_logentry'),(4,'Can view log entry',1,'view_logentry'),(5,'Can add permission',2,'add_permission'),(6,'Can change permission',2,'change_permission'),(7,'Can delete permission',2,'delete_permission'),(8,'Can view permission',2,'view_permission'),(9,'Can add group',3,'add_group'),(10,'Can change group',3,'change_group'),(11,'Can delete group',3,'delete_group'),(12,'Can view group',3,'view_group'),(13,'Can add user',4,'add_user'),(14,'Can change user',4,'change_user'),(15,'Can delete user',4,'delete_user'),(16,'Can view user',4,'view_user'),(17,'Can add content type',5,'add_contenttype'),(18,'Can change content type',5,'change_contenttype'),(19,'Can delete content type',5,'delete_contenttype'),(20,'Can view content type',5,'view_contenttype'),(21,'Can add session',6,'add_session'),(22,'Can change session',6,'change_session'),(23,'Can delete session',6,'delete_session'),(24,'Can view session',6,'view_session'),(25,'Can add Partido Político',7,'add_partidopolitico'),(26,'Can change Partido Político',7,'change_partidopolitico'),(27,'Can delete Partido Político',7,'delete_partidopolitico'),(28,'Can view Partido Político',7,'view_partidopolitico'),(29,'Can add Candidato Presidencial',8,'add_candidatopresidencial'),(30,'Can change Candidato Presidencial',8,'change_candidatopresidencial'),(31,'Can delete Candidato Presidencial',8,'delete_candidatopresidencial'),(32,'Can view Candidato Presidencial',8,'view_candidatopresidencial'),(33,'Can add Diputado',9,'add_candidatodiputado'),(34,'Can change Diputado',9,'change_candidatodiputado'),(35,'Can delete Diputado',9,'delete_candidatodiputado'),(36,'Can view Diputado',9,'view_candidatodiputado'),(37,'Can add Candidato Parlamento Andino',10,'add_candidatoparlamentoandino'),(38,'Can change Candidato Parlamento Andino',10,'change_candidatoparlamentoandino'),(39,'Can delete Candidato Parlamento Andino',10,'delete_candidatoparlamentoandino'),(40,'Can view Candidato Parlamento Andino',10,'view_candidatoparlamentoandino'),(41,'Can add Senador Nacional',11,'add_candidatosenadornacional'),(42,'Can change Senador Nacional',11,'change_candidatosenadornacional'),(43,'Can delete Senador Nacional',11,'delete_candidatosenadornacional'),(44,'Can view Senador Nacional',11,'view_candidatosenadornacional'),(45,'Can add Senador Regional',12,'add_candidatosenadorregional'),(46,'Can change Senador Regional',12,'change_candidatosenadorregional'),(47,'Can delete Senador Regional',12,'delete_candidatosenadorregional'),(48,'Can view Senador Regional',12,'view_candidatosenadorregional'),(49,'Can add Circunscripción',13,'add_circunscripcion'),(50,'Can change Circunscripción',13,'change_circunscripcion'),(51,'Can delete Circunscripción',13,'delete_circunscripcion'),(52,'Can view Circunscripción',13,'view_circunscripcion'),(53,'Can add Voto Simulacro',14,'add_simulacrovoto'),(54,'Can change Voto Simulacro',14,'change_simulacrovoto'),(55,'Can delete Voto Simulacro',14,'delete_simulacrovoto'),(56,'Can view Voto Simulacro',14,'view_simulacrovoto'),(57,'Can add Usuario',15,'add_usuario'),(58,'Can change Usuario',15,'change_usuario'),(59,'Can delete Usuario',15,'delete_usuario'),(60,'Can view Usuario',15,'view_usuario');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(150) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'pbkdf2_sha256$870000$BUYLf6fAFDAvDRqKy1IvS8$64sv4yTmjwzU/GvX13y6rNI4d1RYuSeb6Yb3dB7Jkng=','2025-10-30 02:37:46.251573',1,'juan','','','',1,1,'2025-10-30 02:34:27.322699');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_groups`
--

DROP TABLE IF EXISTS `auth_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`),
  CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_groups`
--

LOCK TABLES `auth_user_groups` WRITE;
/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_user_permissions`
--

DROP TABLE IF EXISTS `auth_user_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_user_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_user_permissions`
--

LOCK TABLES `auth_user_user_permissions` WRITE;
/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatos_diputados`
--

DROP TABLE IF EXISTS `candidatos_diputados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatos_diputados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `foto_url` varchar(500) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `genero` enum('M','F','Otro') NOT NULL,
  `profesion` varchar(200) DEFAULT NULL,
  `experiencia_politica` text DEFAULT NULL,
  `biografia` text DEFAULT NULL,
  `hoja_vida_url` varchar(500) DEFAULT NULL,
  `posicion_lista` int(11) NOT NULL,
  `numero_preferencial` int(11) DEFAULT NULL,
  `es_natural_circunscripcion` tinyint(1) DEFAULT 0,
  `estado` enum('inscrito','observado','excluido','aprobado') DEFAULT 'inscrito',
  `fecha_inscripcion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dni_circunscripcion` (`dni`,`circunscripcion_id`),
  UNIQUE KEY `unique_partido_circunscripcion_posicion` (`partido_id`,`circunscripcion_id`,`posicion_lista`),
  KEY `idx_partido` (`partido_id`),
  KEY `idx_circunscripcion` (`circunscripcion_id`),
  KEY `idx_genero` (`genero`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `candidatos_diputados_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partidos_politicos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `candidatos_diputados_ibfk_2` FOREIGN KEY (`circunscripcion_id`) REFERENCES `circunscripciones` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatos_diputados`
--

LOCK TABLES `candidatos_diputados` WRITE;
/*!40000 ALTER TABLE `candidatos_diputados` DISABLE KEYS */;
INSERT INTO `candidatos_diputados` VALUES (1,2,12,'Dante Alfredo','Chávez Abanto','17922605','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXDuBDlONEtr_ujmxeMM3JZXEsOltYsiFjrg&s','1950-01-01',70,'M','Contador','BUSCAR','BUSCAR','https://pe.linkedin.com/in/dante-ch%C3%A1vez-abanto-800938230',4,5,1,'inscrito','2025-11-05','2025-11-02 09:46:48','2025-11-02 09:46:48');
/*!40000 ALTER TABLE `candidatos_diputados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatos_parlamento_andino`
--

DROP TABLE IF EXISTS `candidatos_parlamento_andino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatos_parlamento_andino` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `foto_url` varchar(500) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `genero` enum('M','F','Otro') NOT NULL,
  `profesion` varchar(200) DEFAULT NULL,
  `experiencia_politica` text DEFAULT NULL,
  `experiencia_internacional` text DEFAULT NULL,
  `biografia` text DEFAULT NULL,
  `hoja_vida_url` varchar(500) DEFAULT NULL,
  `posicion_lista` int(11) NOT NULL,
  `numero_preferencial` int(11) DEFAULT NULL,
  `idiomas` varchar(200) DEFAULT NULL,
  `estado` enum('inscrito','observado','excluido','aprobado') DEFAULT 'inscrito',
  `fecha_inscripcion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `unique_partido_posicion` (`partido_id`,`posicion_lista`),
  KEY `idx_partido` (`partido_id`),
  KEY `idx_genero` (`genero`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `candidatos_parlamento_andino_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partidos_politicos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatos_parlamento_andino`
--

LOCK TABLES `candidatos_parlamento_andino` WRITE;
/*!40000 ALTER TABLE `candidatos_parlamento_andino` DISABLE KEYS */;
INSERT INTO `candidatos_parlamento_andino` VALUES (1,2,'no hay info','no hay info','1234567','https://www.web.onpe.gob.pe/modElecciones/elecciones/elecciones2016/PRPCP2016/Resultados-Ubigeo-Parlamento.html#posicion','2025-10-26',19,'M','no hay info','no hay info','no hay info','no hay info','https://www.web.onpe.gob.pe/modElecciones/elecciones/elecciones2016/PRPCP2016/Resultados-Ubigeo-Parlamento.html#posicion',6,2,'no hay info','inscrito','2025-11-05','2025-11-02 09:49:58','2025-11-02 09:49:58');
/*!40000 ALTER TABLE `candidatos_parlamento_andino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatos_presidenciales`
--

DROP TABLE IF EXISTS `candidatos_presidenciales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatos_presidenciales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `presidente_nombre` varchar(200) NOT NULL,
  `presidente_apellidos` varchar(200) NOT NULL,
  `presidente_dni` varchar(8) NOT NULL,
  `presidente_foto_url` varchar(500) DEFAULT NULL,
  `presidente_fecha_nacimiento` date DEFAULT NULL,
  `presidente_profesion` varchar(200) DEFAULT NULL,
  `presidente_biografia` text DEFAULT NULL,
  `presidente_genero` enum('M','F','Otro') NOT NULL,
  `vicepresidente1_nombre` varchar(200) DEFAULT NULL,
  `vicepresidente1_apellidos` varchar(200) DEFAULT NULL,
  `vicepresidente1_dni` varchar(8) DEFAULT NULL,
  `vicepresidente1_foto_url` varchar(500) DEFAULT NULL,
  `vicepresidente1_fecha_nacimiento` date DEFAULT NULL,
  `vicepresidente1_profesion` varchar(200) DEFAULT NULL,
  `vicepresidente1_biografia` text DEFAULT NULL,
  `vicepresidente1_genero` varchar(10) DEFAULT NULL,
  `vicepresidente2_nombre` varchar(200) DEFAULT NULL,
  `vicepresidente2_apellidos` varchar(200) DEFAULT NULL,
  `vicepresidente2_dni` varchar(8) DEFAULT NULL,
  `vicepresidente2_foto_url` varchar(500) DEFAULT NULL,
  `vicepresidente2_fecha_nacimiento` date DEFAULT NULL,
  `vicepresidente2_profesion` varchar(200) DEFAULT NULL,
  `vicepresidente2_biografia` text DEFAULT NULL,
  `vicepresidente2_genero` varchar(10) DEFAULT NULL,
  `plan_gobierno_url` varchar(500) DEFAULT NULL,
  `numero_lista` int(11) DEFAULT NULL,
  `estado` enum('inscrito','observado','excluido','aprobado') DEFAULT 'inscrito',
  `fecha_inscripcion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `presidente_dni` (`presidente_dni`),
  UNIQUE KEY `vicepresidente1_dni` (`vicepresidente1_dni`),
  UNIQUE KEY `vicepresidente2_dni` (`vicepresidente2_dni`),
  KEY `idx_partido` (`partido_id`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `candidatos_presidenciales_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partidos_politicos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatos_presidenciales`
--

LOCK TABLES `candidatos_presidenciales` WRITE;
/*!40000 ALTER TABLE `candidatos_presidenciales` DISABLE KEYS */;
INSERT INTO `candidatos_presidenciales` VALUES (1,1,'Keiko','Fujimori','10001088','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSircVrljTiJB9rWml00GXmMeBk6-jNbXjIJg&s','1975-05-25','Política/Abogada','Líder de Fuerza Popular; candidata recurrente.','F','Miguel','Torres Morales','40433187','https://upload.wikimedia.org/wikipedia/commons/a/a2/Miguel_Angel_Torres_Morales_2023.jpg','1980-02-18','Político','Excongresista y dirigente de Fuerza Popular.','M','Luis','Galarreta','9537000','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-T0ITgayVbn1Bhu15UG0xjpX5r9t9YAYWtQ&s','1971-03-12','Político','Excongresista y dirigente fujimorista.','M','https://fuerzapopular.com.pe/',27,'inscrito','2025-04-08','2025-10-30 22:19:29','2025-11-04 01:09:39'),(2,2,'Rafael','López Aliaga','7845838','https://saludconlupa.com/media/images/Rafael-Lopez-Aliaga.2e16d0ba.fill-400x400.jpg','1961-02-11','Empresario/Político','Líder y fundador de Renovación Popular.','M','Norma','Yarrow','10806296','https://caretas.pe/wp-content/uploads/2024/02/Norma-Yarrow-1.jpg','1963-07-29','Congresista','Congresista y dirigente de Renovación Popular.','F','Jhon Iván','Ramos Malpica','28566277','https://elcomercio.pe/resizer/v2/SMNQCSVCDBDR5PSIU7O4DZ57KY.JPG?width=6720&height=4480&auth=2027e26a62446907dda3d855c0b9016051a5c3787207094dbcf187063386d098&smart=true','1971-07-15','Político','Militante y dirigente del partido.','M','https://renovacionpopular.com.pe/',14,'inscrito','2025-08-19','2025-11-04 01:09:39','2025-11-04 01:09:39'),(3,3,'César','Acuña Peralta','17903382','https://upload.wikimedia.org/wikipedia/commons/b/bf/C%C3%A9sar_Acu%C3%B1a_Peralta.jpg','1952-08-11','Ingeniero/Empresario','Fundador de Alianza para el Progreso; tres veces candidato.','M','Alejandro','Soto Reyes','23901989','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqdN5mNTv3EVHfAsJnZf8WakX3kCx9ZH2G7g&s','1960-05-24','Congresista','Congresista y miembro de APP.','M','Jessica','Tumi','41357841','https://n60.pe/wp-content/uploads/2024/08/jessica-tumi-riv.jpg','1982-01-19','Política','Militante y figura pública del partido.','F','https://app.pe/',53,'inscrito','2025-02-02','2025-11-04 01:09:39','2025-11-04 01:09:39'),(4,5,'Messias','Guevara','41793865','https://larepublica.cronosmedia.glr.pe/taxonomy/category/image/photos/2023/08/15/ji474a17b6-uevara-2.png','1963-06-13','Político/Gobernador regional','Una de las listas del Partido Morado; aspira a la presidencia.','M','Herber','Cueva','29691345','https://s3-sa-east-1.amazonaws.com/doctoralia.pe/doctor/70e61c/70e61cdcbce0de7f1ce6da8c4fbfbb73_large.jpg','1972-10-29','Político','Militante del partido.','M','Marisol','Liñán','46610430','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ--yRSVzf9HlBDxsDHzi0CuWeYLwwp_Zdf6g&s','1963-10-02','Política','Militante del partido.','F','https://podemosperu.org.pe/',91,'inscrito','2025-06-25','2025-11-04 01:09:39','2025-11-04 01:09:39'),(5,6,'Vladimir','Cerrón','6466585','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNrcFmzzV27iVY-rPmsCd1JmIVfYMsDpH72w&s','1970-12-16','Médico/Político','Fundador de Perú Libre; líder de la izquierda marxista.','M','Flavio','Cruz Mamani','1311614','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4NI7LSe4Xk_YYDTHBUirkorLvbApppLF6bA&s','1968-04-21','Congresista','Congresista y miembro de Perú Libre.','M','Bertha','Rojas López','71583094','https://www.librosperuanos.com/public_files/foto-rojaslopez.jpg','1979-01-13','Dirigente social','Dirigente y figura cercana al líder regional.','F','https://perulibre.pe/',8,'inscrito','2025-11-14','2025-11-04 01:09:39','2025-11-04 01:09:39'),(6,12,'Fiorella','Molinelli','25681995','https://upload.wikimedia.org/wikipedia/commons/9/94/Fiorella_Molinelli.png','1974-03-20','Economista/Funcionaria pública','Ex presidenta de EsSalud y líder de Fuerza Moderna.','F','Gilbert','Violeta','21871411','https://pbs.twimg.com/profile_images/1600533651552354306/shEJYM5x_400x400.jpg','1974-10-19','Político','Exministro y dirigente político.','M','Mariona','Pariona','80001490','https://otorongoclub.s3.us-east-2.amazonaws.com/postulacion_imgs/80523585.png','1979-05-23','Activista','Líder social y activista regional.','F','https://www.fuerzamoderna.org.pe/',76,'inscrito','2025-03-30','2025-11-04 01:09:39','2025-11-04 01:09:39'),(7,8,'Phillip','Butters','9151436','https://pbo.pe/wp-content/uploads/2018/01/PHILLIP-BUTTERS.png','1967-06-13','Periodista/Empresario','Locutor y figura mediática; precandidato por Avanza País.','M','Fernán','Altuve','8274679','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_bWZqRUUVkmBn-9f6_03KE3pW9BUBECqSoA&s','1968-08-31','Político','Dirigente y columnista político.','M','Karol','Paredes','1157063','https://www.expreso.com.pe/wp-content/uploads/2021/09/Karon-PAREDES.jpg','1974-10-09','Congresista','Congresista de Avanza País.','F','https://avanzapais.org.pe/',30,'inscrito','2025-10-17','2025-11-04 01:09:39','2025-11-04 01:09:39'),(8,9,'Roberto','Sánchez','16002918','https://pbs.twimg.com/media/GtDRvMfXMAAvYfy?format=jpg&name=large','1969-02-03','Político/Exministro','Congresista y líder dentro de Juntos por el Perú.','M','Analí','Marquez','45442927','https://otorongoclub.s3.us-east-2.amazonaws.com/postulacion_imgs/45442927.png','1988-10-10','Activista','Activista y dirigente social.','F','Brígida','Curo','2419321','https://losandes.com.pe/wp-content/uploads/2025/10/Brigida-Curo.jpg','1967-02-12','Política','Militante y dirigente del partido.','F','https://jp.org.pe/',45,'inscrito','2025-09-05','2025-11-04 01:09:39','2025-11-04 01:09:39'),(9,10,'Herbert','Caller','43409673','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSP2L-FQj2m7RrKpKUn7Edu7X4UDXmSSxXIQ&s','1978-09-19','Político/Empresario','Encabezó la lista del Partido Patriótico del Perú.','M','Rossana','Montes','10610215','https://derechousmp.com/docentes/cod_contratados/27272727.jpg','1987-07-28','Política','Militante del partido.','F','Jorge','Carcovich','40758358','https://www.expreso.com.pe/wp-content/uploads/2025/10/WhatsApp-Image-2025-10-30-at-20.43.05.jpeg','1959-05-14','Político','Militante del partido.','M','https://partidopatrioticodelperu.pe/',82,'inscrito','2025-01-21','2025-11-04 01:09:39','2025-11-04 01:09:39'),(10,11,'Alfonso','López Chau','25331980','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7naV15EphvbEJHhay6EkTXwqtMU7JgFvW3Q&s','1950-07-17','Académico/Ingeniero','Fundador de Ahora Nación y exrector de la UNI.','M','Luis','Villanueva','17826805','https://prensaperu.pe/wp-content/uploads/2025/03/2-66-1024x689.jpg','1982-05-21','Militante','Dirigente del partido.','M','Ruth','Buendía','80058209','https://files.pucp.education/puntoedu/wp-content/uploads/2024/08/01150417/carita-ruth-buendia-jorge-cerdan-400x600-1.jpg','1977-06-26','Líder indígena','Líder indígena y activista.','F','https://ahoranacion.pe/',69,'inscrito','2025-07-11','2025-11-04 01:09:39','2025-11-04 01:09:39'),(11,7,'Álvaro','Paz de la Barra','41904418','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwDa1xN8Y4xkhR_tzzfERPm3y3H4NGMI_aLA&s','1983-07-26','Político/Abogado','Líder de Fe en el Perú; exfuncionario público.','M','Yessika','Arteaga','40713950','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2HExhD07TwZ8qaYIFPjDL3bWV_1Wv_Q_xdQ&s','1980-05-05','Política','Militante del partido.','F','Shella','Palacios','7203553','https://enfoquenoticias.com.mx/wp-content/uploads/2024/04/WhatsApp-Image-2024-04-25-at-10.10.43.jpeg','1980-10-29','Política','Militante del partido.','F','http://www.partidofrentedelaesperanza2021.pe/',17,'inscrito','2025-05-29','2025-11-04 01:09:39','2025-11-04 01:09:39');
/*!40000 ALTER TABLE `candidatos_presidenciales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatos_senadores_nacionales`
--

DROP TABLE IF EXISTS `candidatos_senadores_nacionales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatos_senadores_nacionales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `foto_url` varchar(500) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `genero` enum('M','F','Otro') NOT NULL,
  `profesion` varchar(200) DEFAULT NULL,
  `experiencia_politica` text DEFAULT NULL,
  `biografia` text DEFAULT NULL,
  `hoja_vida_url` varchar(500) DEFAULT NULL,
  `posicion_lista` int(11) NOT NULL,
  `numero_preferencial` int(11) DEFAULT NULL,
  `estado` enum('inscrito','observado','excluido','aprobado') DEFAULT 'inscrito',
  `fecha_inscripcion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `unique_partido_posicion` (`partido_id`,`posicion_lista`),
  KEY `idx_partido` (`partido_id`),
  KEY `idx_genero` (`genero`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `candidatos_senadores_nacionales_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partidos_politicos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatos_senadores_nacionales`
--

LOCK TABLES `candidatos_senadores_nacionales` WRITE;
/*!40000 ALTER TABLE `candidatos_senadores_nacionales` DISABLE KEYS */;
INSERT INTO `candidatos_senadores_nacionales` VALUES (1,1,'Keiko Sofía','Fujimori Higuchi','10001088','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSircVrljTiJB9rWml00GXmMeBk6-jNbXjIJg&s','1975-05-25',50,'F','Política/Abogada','Líder de Fuerza Popular; candidata presidencial','Líder de Fuerza Popular y candidata recurrente.','https://es.wikipedia.org/wiki/Keiko_Fujimori',1,1,'inscrito','2025-03-07','2025-11-02 08:04:25','2025-11-04 01:37:26'),(2,1,'Luis','Galarreta Velarde','9537000','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-T0ITgayVbn1Bhu15UG0xjpX5r9t9YAYWtQ&s','1971-03-12',54,'M','Abogado/Político','Excongresista, presidente del Congreso 2017-18','Excongresista y dirigente fujimorista.','https://www.congreso.gob.pe/congresistas2011/LuisGalarreta/sobre_congresista/hoja-vida/',2,2,'inscrito','2025-06-29','2025-11-04 01:37:26','2025-11-04 01:37:26'),(3,2,'Rafael','López Aliaga Cazorla','7845838','https://saludconlupa.com/media/images/Rafael-Lopez-Aliaga.2e16d0ba.fill-400x400.jpg','1961-02-11',64,'M','Empresario/Político','Líder de Renovación Popular; exalcalde de Lima','Empresario y líder conservador.','https://es.wikipedia.org/wiki/Rafael_L%C3%B3pez-Aliaga',1,1,'inscrito','2025-01-14','2025-11-04 01:37:26','2025-11-04 01:37:26'),(4,2,'Norma','Yarrow Lumbreras','10806296','https://comunicaciones.congreso.gob.pe/wpuploads/2023/10/51338387032_4011b75e71_k.jpg','1963-07-29',62,'F','Arquitecta/Política','Congresista 2021-2026','Congresista y dirigente de Renovación Popular.','https://es.wikipedia.org/wiki/Norma_Yarrow',2,1,'inscrito','2025-08-18','2025-11-04 01:37:26','2025-11-04 01:37:26'),(5,3,'César','Acuña Peralta','17903382','https://upload.wikimedia.org/wikipedia/commons/b/bf/C%C3%A9sar_Acu%C3%B1a_Peralta.jpg','1952-08-11',73,'M','Ingeniero/Empresario','Fundador de APP; gobernador regional','Fundador y líder de Alianza para el Progreso.','https://es.wikipedia.org/wiki/C%C3%A9sar_Acu%C3%B1a',1,2,'inscrito','2025-05-02','2025-11-04 01:37:26','2025-11-04 01:37:26'),(6,3,'Alejandro','Soto Reyes','23901989','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfT4xVbtZ1NwHRxvreLGMqA6Gju85_p7LXdQ&s','1960-05-24',65,'M','Político','Congresista/funcionario regional','Congresista y miembro de APP.','https://www.congreso.gob.pe/congresistas2021/AlejandroSotoReyes/',2,1,'inscrito','2025-04-25','2025-11-04 01:37:26','2025-11-04 01:37:26'),(7,5,'Julio','Guzmán Cáceres','9337130','https://www.planetadelibros.com.pe/usuaris/autores/fotos/69/original/000068857_1_autor_nuestro_propio_camino_201608151834.jpg','1970-07-31',55,'M','Politólogo','Líder del Partido Morado; excandidato presidencial','Fundador y líder del Partido Morado.','https://es.wikipedia.org/wiki/Julio_Guzm%C3%A1n',1,2,'inscrito','2025-07-10','2025-11-04 01:37:26','2025-11-04 01:37:26'),(8,5,'Carolina','Lizárraga Houghton','9336553','https://www.congreso.gob.pe/Docs/congresistas2016/CarolinaLizarraga/Interface/images/CarolinaLiz%C3%A1rragaWeb02.jpg','1970-03-25',55,'F','Abogada','Excongresista y dirigente','Excongresista y dirigente del movimiento.','https://es.wikipedia.org/wiki/Carolina_Liz%C3%A1rraga',2,2,'inscrito','2025-10-03','2025-11-04 01:37:26','2025-11-04 01:37:26'),(9,6,'Vladimir','Cerrón Rojas','6466585','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNrcFmzzV27iVY-rPmsCd1JmIVfYMsDpH72w&s','1970-12-16',54,'M','Médico/Político','Fundador de Perú Libre; exgobernador regional','Líder regional y fundador de Perú Libre.','https://es.wikipedia.org/wiki/Vladimir_Cerr%C3%B3n',1,1,'inscrito','2025-02-20','2025-11-04 01:37:26','2025-11-04 01:37:26'),(10,6,'Waldemar','Cerrón Rojas','20036514','https://pbs.twimg.com/profile_images/1434720593690169347/G6rD7yqw_400x400.jpg','1972-03-19',53,'M','Político','Congresista y dirigente regional','Congresista y hermano del líder del partido.','https://es.wikipedia.org/wiki/Waldemar_Cerr%C3%B3n',2,2,'inscrito','2025-09-16','2025-11-04 01:37:26','2025-11-04 01:37:26'),(11,12,'Fiorella','Molinelli Aristondo','25681995','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQb-MMM_xUQBq7fLLh-6Dy3aj98mPmBbqvQLg&s','1974-03-20',51,'F','Economista','Exfuncionaria pública; líder de Fuerza Moderna','Ex presidenta de EsSalud y figura pública.','https://es.wikipedia.org/wiki/Fiorella_Molinelli',1,1,'inscrito','2025-03-05','2025-11-04 01:37:26','2025-11-04 01:37:26'),(12,12,'Gilbert','Violeta','21871411','https://pbs.twimg.com/profile_images/1600533651552354306/shEJYM5x_400x400.jpg','1974-10-19',51,'M','Político','Exministro y dirigente','Exministro y figura pública aliado a Fuerza Moderna.','https://www.congreso.gob.pe/congresistas2016/GilbertVioleta/Hojavida/',2,1,'inscrito','2025-06-22','2025-11-04 01:37:26','2025-11-04 01:37:26'),(13,8,'Hernando','de Soto Polar','9155583','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6wfwPoBnS4tpOjHM-WHlpWGRdjOrYHCm00Q&s','1941-06-02',84,'M','Economista','Economista y figura pública; candidato en 2021','Economista reconocido internacionalmente.','https://es.wikipedia.org/wiki/Hernando_de_Soto_(economista)',1,2,'inscrito','2025-01-11','2025-11-04 01:37:26','2025-11-04 01:37:26'),(14,8,'Phillip','Butters','9151436','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTurIcqYrhwZh-E8rMm69RyQDY2J-a6Amm7PA&s','1967-06-13',58,'M','Periodista/Empresario','Figura mediática','Locutor y figura mediática.','https://es.wikipedia.org/wiki/Phillip_Butters',2,2,'inscrito','2025-08-19','2025-11-04 01:37:26','2025-11-04 01:37:26'),(15,9,'Verónika','Mendoza Frisch','40765768','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAFtX4dVErNSm6cV4FFSfkEaiINzTC0OkTWQ&s','1980-12-09',44,'F','Antropóloga/Política','Excongresista y excandidata presidencial','Líder de la izquierda progresista.','https://es.wikipedia.org/wiki/Ver%C3%B3nika_Mendoza',1,1,'inscrito','2025-05-04','2025-11-04 01:37:26','2025-11-04 01:37:26'),(16,9,'Roberto','Sánchez Palomino','16002918','https://portal.andina.pe/EDPfotografia3/Thumbnail/2022/02/01/000843201M.webp','1969-02-03',56,'M','Psicólogo/Político','Exministro y dirigente','Político y exministro.','https://es.wikipedia.org/wiki/Roberto_S%C3%A1nchez_Palomino',2,2,'inscrito','2025-04-28','2025-11-04 01:37:26','2025-11-04 01:37:26'),(17,10,'Herbert','Caller','43409673','https://media.licdn.com/dms/image/v2/C4E03AQEE9NCiwzobWA/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1590757931338?e=2147483647&v=beta&t=qzsNoCEyQCMP6euqOtJrqqX_kUf8a9vcJWWqrd6z8MA','1978-09-19',47,'M','Abogado/Exmilitar','Líder del partido','Encabezó la lista del Partido Patriótico del Perú.','https://otorongo.club/2021/candidate/43409673/',1,1,'inscrito','2025-07-13','2025-11-04 01:37:26','2025-11-04 01:37:26'),(18,10,'Rossana','Montes Tello','10610215','https://derechousmp.com/docentes/cod_contratados/27272727.jpg','1987-07-28',38,'F','Política','Militante del partido','Militante y dirigente del partido.','https://derecho.usmp.edu.pe/facultad/docentes_contratados/',2,1,'inscrito','2025-10-01','2025-11-04 01:37:26','2025-11-04 01:37:26'),(19,11,'Álvaro','Paz de la Barra Freigeiro','41904418','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwDa1xN8Y4xkhR_tzzfERPm3y3H4NGMI_aLA&s','1983-07-26',42,'M','Político/Empresario','Figura local y fundador del movimiento','Líder del movimiento Ahora Nación.','https://es.wikipedia.org/wiki/%C3%81lvaro_Paz_de_la_Barra',1,2,'inscrito','2025-02-26','2025-11-04 01:37:26','2025-11-04 01:37:26'),(20,11,'Alfonso','López Chau','25331980','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfl0SMG9M4EzQVk1EA4IMLveH3-RBxVeQnaoTmQJgwEjMTfUJ8KKZOqrJ4m1xkhi4OnMw&usqp=CAU','1950-07-17',75,'M','Académico/Ingeniero','Fundador del movimiento','Académico y líder del movimiento Ahora Nación.','https://www.congreso.gob.pe/congresistas2000/fernando_olivera/curriculum/',2,1,'inscrito','2025-09-12','2025-11-04 01:37:26','2025-11-04 01:37:26'),(21,7,'Fernando','Olivera Vega','6280714','https://portal.andina.pe/EDPfotografia/Thumbnail/2015/09/14/000313912W.jpg','1958-07-26',67,'M','Abogado/Político','Fundador y exministro','Líder del Frente Esperanza; exministro de Justicia.','https://www.congreso.gob.pe/congresistas2000/fernando_olivera/curriculum/',1,2,'inscrito','2025-03-09','2025-11-04 01:37:26','2025-11-04 01:37:26'),(22,7,'Yessika','Arteaga','40713950','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2HExhD07TwZ8qaYIFPjDL3bWV_1Wv_Q_xdQ&s','1980-05-05',45,'F','Política','Militante del partido','Militante y dirigente del Frente Esperanza.','https://otorongo.club/2022/candidate/40713950/',2,2,'inscrito','2025-06-24','2025-11-04 01:37:26','2025-11-04 01:37:26');
/*!40000 ALTER TABLE `candidatos_senadores_nacionales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidatos_senadores_regionales`
--

DROP TABLE IF EXISTS `candidatos_senadores_regionales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatos_senadores_regionales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `foto_url` varchar(500) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `genero` enum('M','F','Otro') NOT NULL,
  `profesion` varchar(200) DEFAULT NULL,
  `experiencia_politica` text DEFAULT NULL,
  `biografia` text DEFAULT NULL,
  `hoja_vida_url` varchar(500) DEFAULT NULL,
  `posicion_lista` int(11) NOT NULL,
  `numero_preferencial` int(11) DEFAULT NULL,
  `es_natural_circunscripcion` tinyint(1) DEFAULT 0,
  `estado` enum('inscrito','observado','excluido','aprobado') DEFAULT 'inscrito',
  `fecha_inscripcion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dni_circunscripcion` (`dni`,`circunscripcion_id`),
  UNIQUE KEY `unique_partido_circunscripcion_posicion` (`partido_id`,`circunscripcion_id`,`posicion_lista`),
  KEY `idx_partido` (`partido_id`),
  KEY `idx_circunscripcion` (`circunscripcion_id`),
  KEY `idx_genero` (`genero`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `candidatos_senadores_regionales_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partidos_politicos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `candidatos_senadores_regionales_ibfk_2` FOREIGN KEY (`circunscripcion_id`) REFERENCES `circunscripciones` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatos_senadores_regionales`
--

LOCK TABLES `candidatos_senadores_regionales` WRITE;
/*!40000 ALTER TABLE `candidatos_senadores_regionales` DISABLE KEYS */;
INSERT INTO `candidatos_senadores_regionales` VALUES (1,2,12,'Richard Frank','Acuña Núñez','42971376','https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Richard_Acu%C3%B1a.jpg/320px-Richard_Acu%C3%B1a.jpg','1984-09-18',41,'M','Empresario','Richard Acuña tiene experiencia política como congresista (entre 2011 y 2016), donde fue presidente de la Comisión de Descentralización. Su trayectoria incluye desde su juventud, coordinando campañas de su padre y participando en actividades políticas, hasta su rol como miembro de diversas comisiones en el Congreso, presentando y logrando la aprobación de proyectos de ley.','Richard Acuña es un político peruano con experiencia en el ámbito legislativo y empresarial. Ha sido congresista de la República, miembro de diversas comisiones, y ocupó cargos gerenciales en la Universidad César Vallejo (UCV) y en su empresa de seguridad. Desde 2011, se desempeña como Congresista, habiendo sido elegido en varias ocasiones.','https://pe.linkedin.com/in/richard-acu%C3%B1a-n%C3%BA%C3%B1ez-07b07553',2,3,1,'inscrito',NULL,'2025-11-02 09:41:30','2025-11-02 09:41:30');
/*!40000 ALTER TABLE `candidatos_senadores_regionales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `circunscripciones`
--

DROP TABLE IF EXISTS `circunscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `circunscripciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `codigo` varchar(10) NOT NULL,
  `tipo` enum('departamento','provincia_constitucional','exterior') NOT NULL,
  `poblacion` int(11) DEFAULT NULL,
  `electores_registrados` int(11) DEFAULT NULL,
  `capital` varchar(100) DEFAULT NULL,
  `imagen_url` varchar(500) DEFAULT NULL,
  `bandera_url` varchar(500) DEFAULT NULL,
  `escudo_url` varchar(500) DEFAULT NULL,
  `numero_diputados` int(11) DEFAULT 1,
  `numero_senadores` int(11) DEFAULT 1,
  `latitud` decimal(10,8) DEFAULT NULL,
  `longitud` decimal(11,8) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `idx_tipo` (`tipo`),
  KEY `idx_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `circunscripciones`
--

LOCK TABLES `circunscripciones` WRITE;
/*!40000 ALTER TABLE `circunscripciones` DISABLE KEYS */;
INSERT INTO `circunscripciones` VALUES (1,'Amazonas','AMA','departamento',429943,319326,'Chachapoyas','https://upload.wikimedia.org/wikipedia/commons/8/8f/Chachapoyas.jpg','https://www.shutterstock.com/shutterstock/videos/1109582805/thumb/1.jpg?ip=x480','https://cpng.pikpng.com/pngl/s/383-3834161_smbolos-de-amazonas-gobierno-regional-amazonas-clipart.png',2,1,-5.66666667,-78.50000000,'Amazonas, con capital Chachapoyas, destaca por Kuélap y su selva montañosa.','2025-10-29 22:19:34'),(2,'Áncash','ANC','departamento',1083519,910770,'Huaraz','https://content.r9cdn.net/rimg/dimg/17/1d/38963bbb-city-58607-173326d42bf.jpg?crop=true&width=1020&height=498','https://cpng.pikpng.com/pngl/s/383-3834161_smbolos-de-amazonas-gobierno-regional-amazonas-clipart.png','https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Coat_of_Armsof_Ancash_Department%2C_Peru.svg/676px-Coat_of_Armsof_Ancash_Department%2C_Peru.svg.png',5,1,-9.53333333,-77.60000000,'Áncash, con capital Huaraz, es conocido por la Cordillera Blanca, el Huascarán y sus paisajes andinos.','2025-10-29 22:19:34'),(3,'Apurímac','APU','departamento',430838,351743,'Abancay','https://noticias-pe.laiglesiadejesucristo.org/media/960x540/Abancay-Apurimac-2025.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Bandera_Regi%C3%B3n_Apurimac.svg/2560px-Bandera_Regi%C3%B3n_Apurimac.svg.png','https://upload.wikimedia.org/wikipedia/commons/5/54/Escudo_Regi%C3%B3n_Apur%C3%ADmac.png',2,1,-13.61666667,-72.86666667,'Apurímac es una región andina del sur de Perú, conocida por sus profundos cañones, ríos y tradiciones culturales.','2025-10-29 22:19:34'),(4,'Arequipa','ARE','departamento',1594091,1162967,'C. Arequipa','https://www.peru.travel/Contenido/Destino/Imagen/es/3/1.2/Principal/Plaza%20de%20Armas%20Arequipa.home%20_2_.jpg','https://upload.wikimedia.org/wikipedia/commons/d/d5/Bandera_Arequipa_Per%C3%BA.png','https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Escudo_de_Arequipa.svg/1827px-Escudo_de_Arequipa.svg.png',6,1,-16.40000000,-71.53333333,'Arequipa, ubicada en el sur de Perú, es famosa por su arquitectura colonial en sillar blanco, el volcán Misti y su importante actividad económica y turística.','2025-10-29 22:19:34'),(5,'Ayacucho','AYA','departamento',677190,493358,'Huamanga','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTq5JhX9pUce14fu-skcOM4tsBuOYwyyQDdpA&s','https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Flag_of_Ayacucho.svg/2560px-Flag_of_Ayacucho.svg.png','https://upload.wikimedia.org/wikipedia/commons/1/12/Escudo_de_Ayacucho.jpg',3,1,-13.15000000,-74.21666667,'Ayacucho es una región andina del sur de Perú, reconocida por su historia, arquitectura colonial, fiestas religiosas y tradiciones culturales.','2025-10-29 22:19:34'),(6,'Cajamarca','CAJ','departamento',1341012,1205585,'C. Cajamarca','https://elperuano.pe/fotografia/thumbnail/2022/01/03/000145568M.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Bandera_de_Cajamarca.svg/2560px-Bandera_de_Cajamarca.svg.png','https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Escudo_de_Cajamarca.svg/1792px-Escudo_de_Cajamarca.svg.png',6,1,-7.15000000,-78.51666667,'Cajamarca, en el norte de Perú, es conocida por sus paisajes andinos, baños termales y su relevancia histórica durante la conquista española.','2025-10-29 22:19:34'),(7,'Cusco','CUS','departamento',1205527,1052147,'Qosqo','https://picchutravel.com/wp-content/uploads/que-hacer-en-la-ciudad-de-cusco.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Flag_of_Cusco_%282021%29.svg/500px-Flag_of_Cusco_%282021%29.svg.png','https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/COA_of_Cuzco.jpg/250px-COA_of_Cuzco.jpg',6,1,-13.51666667,-71.96666667,'Cusco, en el sureste de Perú, fue la capital del Imperio Inca y es famosa por Machu Picchu, su historia y cultura andina viva.','2025-10-29 22:19:34'),(8,'Huancavelica','HUA','departamento',347639,322628,'C. Huancavelica','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDONgDyoUIsN-bL4z-dFLSnjT2OhsuUpKYmg&s','https://upload.wikimedia.org/wikipedia/commons/a/a7/Bandera_de_Huancavelica.png','https://upload.wikimedia.org/wikipedia/commons/0/0e/Escudo_de_Hu%C3%A1ncavelica.png',2,1,-12.78333333,-74.98333333,'Huancavelica, en la sierra central de Perú, se caracteriza por su altitud, clima frío y actividad minera tradicional.','2025-10-29 22:19:34'),(9,'Huánuco','HUC','departamento',721047,611488,'C. Huanuco','https://pagina3.pe/wp-content/uploads/2024/08/Huanuco-1.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Flag_of_Hu%C3%A1nuco.svg/1280px-Flag_of_Hu%C3%A1nuco.svg.png','https://upload.wikimedia.org/wikipedia/commons/4/44/COA_Huanuco.png',4,1,-9.93333333,-76.23333333,'Huánuco, en la región central del Perú, combina valles fértiles y zonas montañosas, destacando por su agricultura y tradiciones culturales.','2025-10-29 22:19:34'),(10,'Ica','ICA','departamento',1062346,663357,'C. Ica','https://noticias-pe.laiglesiadejesucristo.org/media/960x540/ciudad-de-ica-plaza-de-armas.png','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Bandera_Regi%C3%B3n_Ica.svg/1200px-Bandera_Regi%C3%B3n_Ica.svg.png','https://upload.wikimedia.org/wikipedia/commons/a/a6/Escudo_Regi%C3%B3n_Ica.png',4,1,-14.06666667,-75.71666667,'Ica, en la costa sur del Perú, es conocida por sus viñedos, oasis de Huacachina y su clima desértico.','2025-10-29 22:19:34'),(11,'Junín','JUN','departamento',1377838,998021,'Huancayo','https://estudiantes.ucontinental.edu.pe/wp-content/uploads/2019/02/foto-de-huancayo-peru-uc-portada.jpg','https://upload.wikimedia.org/wikipedia/commons/b/b5/Bandera_Jun%C3%ADn.png','https://upload.wikimedia.org/wikipedia/commons/6/67/Escudo_regional_Jun%C3%ADn.png',6,1,-11.11666667,-75.20000000,'Junín, en la sierra central de Perú, destaca por sus lagos, agricultura, ganadería y actividad minera.','2025-10-29 22:19:34'),(12,'La Libertad','LAL','departamento',1778080,1371801,'Trujillo','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQE2jkfK0L9bBdSn8PBBhLwsULgqqQ-C9_hrQ&s','https://www.shutterstock.com/shutterstock/videos/1109582727/thumb/1.jpg?ip=x480','https://upload.wikimedia.org/wikipedia/commons/4/4b/Escudo_Regi%C3%B3n_La_Libertad.png',8,1,-8.10000000,-79.03333333,'Es conocida por Trujillo, Chan Chan y su riqueza arqueológica y cultural.','2025-10-29 22:19:34'),(13,'Lambayeque','LAM','departamento',1197260,858080,'Chiclayo','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSexQKGXssHQFZjX-OGzlBFyGYS5i5cx0VXXg&s','https://ab-ad.eu/145917-thickbox_default/bandera-del-departamento-de-lambayeque-peru.jpg','https://upload.wikimedia.org/wikipedia/commons/1/17/Escudo_de_la_Provincia_de_Lambayeque.png',6,1,-6.73333333,-79.83333333,'Lambayeque, en la costa norte de Perú, es famosa por sus sitios arqueológicos, como Túcume y el Señor de Sipán, y su agricultura y comercio.','2025-10-29 22:19:34'),(14,'Lima','LIM','departamento',10432133,6377843,'C. Lima','https://dynamic-media-cdn.tripadvisor.com/media/photo-o/12/6a/a0/24/20180322-145842-largejpg.jpg?w=900&h=-1&s=1','https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Flag_of_Lima.svg/1200px-Flag_of_Lima.svg.png','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcsNA-AExQDL6mpJ9cyeTS-hjdUXVR9NoXKQ&s',38,4,-12.03333333,-77.05000000,'Lima, en la costa central de Perú, es la capital del país, centro político, económico y cultural, con importante actividad comercial e histórica.','2025-10-29 22:19:34'),(15,'Loreto','LOR','departamento',1044884,584226,'Iquitos','https://www.peru.travel/norte-del-peru/complemento/images/destinos/interna/loreto-a1.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Bandera_Regi%C3%B3n_Loreto.svg/2560px-Bandera_Regi%C3%B3n_Loreto.svg.png','https://upload.wikimedia.org/wikipedia/commons/b/b0/Escudo_Regi%C3%B3n_Loreto.png',4,1,-4.00000000,-73.50000000,'Loreto, en la Amazonía peruana, es la región más grande del país, con abundante selva, ríos navegables y gran biodiversidad.','2025-10-29 22:19:34'),(16,'Madre de Dios','MDD','departamento',141070,114887,'Puerto Maldonado','https://consultasenlinea.mincetur.gob.pe/fichaInventario/foto.aspx?cod=485193','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSB4OXrXVagS-CFDqlx7xGihAofzThf8tZifg&s','https://upload.wikimedia.org/wikipedia/commons/2/23/Madre_de_Dios_Peru_Escudo.png',2,1,-12.56666667,-69.18333333,'Madre de Dios, en la Amazonía sur del Perú, destaca por su selva tropical, biodiversidad y actividades ecoturísticas y mineras.','2025-10-29 22:19:34'),(17,'Moquegua','MOQ','departamento',203000,132404,'Moquegua','https://dynamic-media-cdn.tripadvisor.com/media/photo-o/14/54/42/05/dsc00418-largejpg.jpg?w=1200&h=-1&s=1','https://upload.wikimedia.org/wikipedia/commons/e/ef/Bandera_Moquegua_Per%C3%BA.png','https://upload.wikimedia.org/wikipedia/commons/4/4d/Escudo_Moquegua_Per%C3%BA.png',2,1,-17.18333333,-70.93333333,'Moquegua, en el sur de Perú, es conocida por su producción agrícola, viñedos, minería y paisajes desérticos y montañosos.','2025-10-29 22:19:34'),(18,'Pasco','PAS','departamento',270842,239445,'Cerro de Pasco','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnFms3DTNexxTNTV9p11jRFNxFtfkpK_SuIA&s','https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Flag_of_Pasco_Department.svg/2560px-Flag_of_Pasco_Department.svg.png','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF2MfiHSHoSSAVwTUdI_22GQuzGDv1orPkRg&s',2,1,-10.68333333,-76.25000000,'Pasco, en la sierra central del Perú, se caracteriza por su minería, altitud elevada y clima frío andino.','2025-10-29 22:19:34'),(19,'Piura','PIU','departamento',2149978,1034031,'C. Piura','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG9ll3YR79mT9b0qZ3q5ELrA-aE_3O_ZBvyg&s','https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Bandera_de_Piura.svg/1200px-Bandera_de_Piura.svg.png','https://upload.wikimedia.org/wikipedia/commons/d/d9/Escudo_de_Piura.png',8,1,-5.18333333,-80.61666667,'Piura, en la costa norte de Perú, es conocida por sus playas, clima cálido, agricultura y tradiciones culturales como la gastronomía y la artesanía.','2025-10-29 22:19:34'),(20,'Puno','PUN','departamento',1199636,814973,'C. Puno','https://consultasenlinea.mincetur.gob.pe/fichaInventario/foto.aspx?cod=485821','https://imgv2-1-f.scribdassets.com/img/document/393727611/original/3f62dc8ba2/1?v=1','https://upload.wikimedia.org/wikipedia/commons/2/2a/Escudo_de_Puno.png',6,1,-15.83333333,-70.01666667,'Puno, en el sur de Perú, es famosa por el lago Titicaca, su cultura andina y festividades tradicionales como la Fiesta de la Candelaria.','2025-10-29 22:19:34'),(21,'San Martín','SAM','departamento',935194,686895,'Moyobamba','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwYdGAoZGvTI_YALF8RIYdT738_gX9vQnf_w&s','https://upload.wikimedia.org/wikipedia/commons/6/63/Bandera_Regi%C3%B3n_San_Mart%C3%ADn.png','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT24PYbkLxEfoMqyx9ha1d2N182sZiJb04l-g&s',4,1,-6.43333333,-76.96666667,'San Martín, en la Amazonía norte del Perú, se distingue por su selva, biodiversidad, cultivos de café y cacao, y atractivos turísticos naturales.','2025-10-29 22:19:34'),(22,'Tacna','TAC','departamento',346000,287014,'C. Tacna','https://content.emarket.pe/common/collections/content/84/52/84520e84-9ea3-415f-a33a-827fd1889814.png','https://thumbs.dreamstime.com/b/bandera-de-tacna-per%C3%BA-ilustraci%C3%B3n-d-89579170.jpg','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZm7jpEMDDleFZTucB6fMyiaOpe9uLKWNx7g&s',2,1,-18.00000000,-70.23333333,'Tacna, en el sur de Perú, es conocida por su comercio, historia patriótica y clima árido, con paisajes desérticos y valles fértiles.','2025-10-29 22:19:34'),(23,'Tumbes','TUM','departamento',270000,168423,'C. Tumbes','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWCxsGu0nFDQaC5YMmaa0TXe7SNAWDgkpJ9g&s','https://upload.wikimedia.org/wikipedia/commons/2/2c/Bandera_de_Tumbes.png','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQA3NsPTrsvnM2LkJv5cNF0ibwDhxXU9Xb--Q&s',2,1,-3.56666667,-80.45000000,'Tumbes, en el extremo norte del Perú, destaca por sus manglares, playas, clima cálido y biodiversidad costera y amazónica.','2025-10-29 22:19:34'),(24,'Ucayali','UCA','departamento',496459,318825,'Pucallpa','https://noticias-pe.laiglesiadejesucristo.org/media/960x540/Pucallpa-Plaza-Armasx-2.jpg','https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Ucayali_flag.png/1200px-Ucayali_flag.png','https://upload.wikimedia.org/wikipedia/commons/5/5f/Escudo_Regional_de_Ucayali.png',2,1,-8.38333333,-74.53333333,'En la Amazonía central de Perú, se caracteriza por su selva tropical, ríos navegables y riqueza en biodiversidad.','2025-10-29 22:19:34'),(25,'Lima Provincias','LIP','provincia_constitucional',644000,205000,'C. Lima','no aplica','no aplica','no aplica',4,1,-11.00000000,-76.13333333,'Las provincias de Lima rodean la capital y combinan pueblos andinos y costeros, con agricultura, pesca y comercio local como principales actividades.','2025-10-29 22:19:34'),(26,'Callao','CAL','provincia_constitucional',1158743,824496,'C. Callao','https://cloudfront-us-east-1.images.arcpublishing.com/infobae/QIJJOARX7VHIJM73BJTTDOATP4.png','https://upload.wikimedia.org/wikipedia/commons/6/61/Bandera_Regi%C3%B3n_Callao.png','https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Escudo_Callao.svg/250px-Escudo_Callao.svg.png',4,1,-12.05000000,-77.10000000,'Callao es el principal puerto marítimo del Perú, vecino de Lima, y destaca por su actividad comercial, naval y aeroportuaria.','2025-10-29 22:19:34'),(27,'Peruanos en el Extranjero','EXT','exterior',3505511,3505511,'Extranjero','no aplica','no aplica','no aplica',2,NULL,NULL,NULL,'no aplica','2025-10-29 22:19:34');
/*!40000 ALTER TABLE `circunscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `denuncias`
--

DROP TABLE IF EXISTS `denuncias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `denuncias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) DEFAULT NULL,
  `tipo_candidato` enum('presidencial','senador_nacional','senador_regional','diputado','parlamento_andino') DEFAULT NULL,
  `titulo` varchar(300) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `tipo_denuncia` varchar(100) DEFAULT NULL,
  `fecha_denuncia` date DEFAULT NULL,
  `entidad_denunciante` varchar(200) DEFAULT NULL,
  `fuente` varchar(300) DEFAULT NULL,
  `url_fuente` varchar(500) DEFAULT NULL,
  `estado_proceso` enum('investigando','archivada','comprobada','sentenciado','absuelto') DEFAULT 'investigando',
  `monto_involucrado` decimal(15,2) DEFAULT NULL,
  `documento_url` varchar(500) DEFAULT NULL,
  `gravedad` enum('leve','moderada','grave','muy_grave') DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_tipo_candidato` (`tipo_candidato`),
  KEY `idx_estado_proceso` (`estado_proceso`),
  KEY `idx_gravedad` (`gravedad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `denuncias`
--

LOCK TABLES `denuncias` WRITE;
/*!40000 ALTER TABLE `denuncias` DISABLE KEYS */;
/*!40000 ALTER TABLE `denuncias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_admin_log`
--

DROP TABLE IF EXISTS `django_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext DEFAULT NULL,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL CHECK (`action_flag` >= 0),
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`),
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_content_type`
--

DROP TABLE IF EXISTS `django_content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (1,'admin','logentry'),(3,'auth','group'),(2,'auth','permission'),(4,'auth','user'),(9,'candidatos','candidatodiputado'),(10,'candidatos','candidatoparlamentoandino'),(8,'candidatos','candidatopresidencial'),(11,'candidatos','candidatosenadornacional'),(12,'candidatos','candidatosenadorregional'),(13,'circunscripciones','circunscripcion'),(5,'contenttypes','contenttype'),(7,'partidos','partidopolitico'),(6,'sessions','session'),(14,'simulacro','simulacrovoto'),(15,'usuarios','usuario');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_migrations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'contenttypes','0001_initial','2025-10-30 02:25:51.448912'),(2,'auth','0001_initial','2025-10-30 02:25:51.624021'),(3,'admin','0001_initial','2025-10-30 02:25:51.663281'),(4,'admin','0002_logentry_remove_auto_add','2025-10-30 02:25:51.668229'),(5,'admin','0003_logentry_add_action_flag_choices','2025-10-30 02:25:51.672676'),(6,'contenttypes','0002_remove_content_type_name','2025-10-30 02:25:51.708298'),(7,'auth','0002_alter_permission_name_max_length','2025-10-30 02:25:51.730251'),(8,'auth','0003_alter_user_email_max_length','2025-10-30 02:25:51.741990'),(9,'auth','0004_alter_user_username_opts','2025-10-30 02:25:51.746438'),(10,'auth','0005_alter_user_last_login_null','2025-10-30 02:25:51.765490'),(11,'auth','0006_require_contenttypes_0002','2025-10-30 02:25:51.766726'),(12,'auth','0007_alter_validators_add_error_messages','2025-10-30 02:25:51.772523'),(13,'auth','0008_alter_user_username_max_length','2025-10-30 02:25:51.785359'),(14,'auth','0009_alter_user_last_name_max_length','2025-10-30 02:25:51.798992'),(15,'auth','0010_alter_group_name_max_length','2025-10-30 02:25:51.814136'),(16,'auth','0011_update_proxy_permissions','2025-10-30 02:25:51.820037'),(17,'auth','0012_alter_user_first_name_max_length','2025-10-30 02:25:51.833422'),(18,'partidos','0001_initial','2025-10-30 02:26:32.886400'),(19,'circunscripciones','0001_initial','2025-10-30 02:26:32.887564'),(20,'candidatos','0001_initial','2025-10-30 02:26:32.888282'),(21,'sessions','0001_initial','2025-10-30 02:26:32.889034'),(22,'simulacro','0001_initial','2025-10-30 02:26:32.889713'),(23,'usuarios','0001_initial','2025-10-30 02:26:32.890439');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_session`
--

DROP TABLE IF EXISTS `django_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_expire_date_a5c62663` (`expire_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
INSERT INTO `django_session` VALUES ('kzn7x4qhu0e0aoa89i6ofheksibpdjsg','.eJxVjMsOwiAQRf-FtSFlShFcuu83kGFmkKqBpI-V8d-1SRe6veec-1IRt7XEbZE5TqwuyqjT75aQHlJ3wHest6ap1XWekt4VfdBFj43leT3cv4OCS_nW0Kcg4sAmQ5a8t3j2KAIdUsgDBkfBhR7ADMSdTd4QZ0rZMA_gDGT1_gDvQzhe:1vEIXq:2pk-q7RC-FUH6bonoBdpMLDi_-8hoJm67YVMmTouG8k','2025-11-13 02:37:46.256057');
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partidos_politicos`
--

DROP TABLE IF EXISTS `partidos_politicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partidos_politicos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `siglas` varchar(50) NOT NULL,
  `logo_url` varchar(500) DEFAULT NULL,
  `color_principal` varchar(50) DEFAULT NULL,
  `tipo` enum('partido','alianza') DEFAULT 'partido',
  `ideologia` varchar(100) DEFAULT NULL,
  `lider` varchar(200) DEFAULT NULL,
  `secretario_general` varchar(200) DEFAULT NULL,
  `fundacion_año` int(11) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `sitio_web` varchar(200) DEFAULT NULL,
  `estado` enum('activo','inactivo','inhabilitado') DEFAULT 'activo',
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `siglas` (`siglas`),
  KEY `idx_estado` (`estado`),
  KEY `idx_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partidos_politicos`
--

LOCK TABLES `partidos_politicos` WRITE;
/*!40000 ALTER TABLE `partidos_politicos` DISABLE KEYS */;
INSERT INTO `partidos_politicos` VALUES (1,'Fuerza Popular','FPOP','https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Fuerza_popular.svg/2048px-Fuerza_popular.svg.png','#F57D1F','partido','Derecha populista','Keiko Fujimori','Luis Galarreta Velarde',2010,'Partido fundado por Keiko Fujimori con orientación populista de derecha.','https://fuerzapopular.com.pe/','activo','2025-10-30 07:33:12','2025-11-03 23:43:15'),(2,'Renovación Popular','RENP','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrr99X6xWP_YWW_6PoyvgrGAvRGgZ1Gn7h-Q&s','#18A0D9','partido','Derecha conservadora','Rafael López Aliaga','José Cueto Aservi',2020,'Movimiento liderado por Rafael López Aliaga de ideología conservadora.','https://renovacionpopular.com.pe/','activo','2025-10-30 21:01:40','2025-11-03 23:43:15'),(3,'Alianza Para el Progreso','APP','https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Alianza_para_el_Progreso_Peru.svg/250px-Alianza_para_el_Progreso_Peru.svg.png','#3b82f6','partido','Centro-Derecha','César Acuña Peralta','Luis Alberto Valdez Farías',2001,'Alianza Para el Progreso (APP) es un partido político nacional fundado el 8 de diciembre del 2001 en la ciudad de Trujillo por César Acuña Peralta. Nació con el propósito de representar a los jóvenes, mujeres y emprendedores marginados por los partidos tradicionales, bajo la visión de construir una verdadera democracia con justicia social y descentralización.','https://app.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(4,'Podemos Perú','PP','https://pbs.twimg.com/profile_images/1932536369743564801/M62CMTUf_400x400.jpg','#bf0d0d','partido','Centro','José Luna Gálvez','Carlos Anderson Ramírez',2018,'Agrupación de centro con enfoque en desarrollo social y económico.','https://podemosperu.org.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:29'),(5,'Partido Morado','PMOR','https://upload.wikimedia.org/wikipedia/commons/8/83/Partido_Morado.png','#ba1717','partido','Centro liberal','Julio Guzmán','Carolina Lizárraga Houghton',2016,'Partido liberal progresista orientado a la modernización del país.','https://partidomorado.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:25'),(6,'Partido Liberal del Perú','PLIB','https://d1yjjnpx0p53s8.cloudfront.net/styles/logo-thumbnail/s3/042023/logomarca_partido_liberal_brasil.png?Kh70Y9RrKOVApb7H4d0kDbP_4Pxrw1KC&itok=sgLX1WGN','#18A0D9','partido','Centro-izquierda','Gino Costa Santolalla','Alberto de Belaunde',2018,'Organización política de centro-izquierda enfocada en derechos civiles.','https://es.wikipedia.org/wiki/Partido_Liberal_(Per%C3%BA)','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(7,'Frente de la Esperanza 2021','FEP','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLC0YfXyaN4irTz3dGCBojuSGthgyt0MOctQ&s','#000000','partido','Democracia cristiana','Fernando Olivera Vega','Carlos Torres Caro',2020,'Partido inspirado en valores cristianos y democráticos.','http://www.partidofrentedelaesperanza2021.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(8,'Avanza País','AVP','https://upload.wikimedia.org/wikipedia/commons/4/42/Avanza_Pais.png','#1935A2','partido','Derecha liberal','Hernando de Soto','Carlos Añaños Jerí',2000,'Movimiento liberal de derecha que promueve el libre mercado.','https://avanzapais.org.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(9,'Juntos por el Perú','JP','https://images.seeklogo.com/logo-png/39/1/juntos-por-el-peru-logo-png_seeklogo-398233.png','#FF0000','partido','Izquierda progresista','Verónika Mendoza Frisch','Roberto Sánchez Palomino',2017,'Coalición de izquierda progresista con enfoque social.','https://www.juntosporelperu.org.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(10,'Partido Patriótico del Perú','PPAT','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwso1KCFokOsk8kfB1IMNfeuegibaiELbBCw&s','#18A0D9','partido','Nacionalista','Antauro Humala Tasso','Germán Alejo Apaza',2019,'Partido nacionalista con discurso popular y militarista.','https://partidopatrioticodelperu.pe/','activo','2025-11-03 23:36:08','2025-11-03 23:43:37'),(11,'Acción Nacional','AN','https://www.roc21.com/wp-content/uploads/2025/10/Logo-Partido-Accion-Nacional-PAN.webp','#00008B','partido','Centro-derecha','Luis Castañeda Lossio','Miguel Romero Sotelo',1956,'Partido tradicional con tendencia centroderechista.','https://es.wikipedia.org/wiki/Partido_Acci%C3%B3n_Nacional','activo','2025-11-03 23:36:08','2025-11-03 23:43:15'),(12,'Fuerza Moderna','FMO','https://www.fuerzamoderna.org.pe/wp-content/uploads/2024/06/8.-Logo.png','#0a2b61','partido','Centro','Fiorella Molinelli','Janet Sanchéz',2024,'FUERZA MODERNA  es un partido político que busca reconstruir la confianza en el Perú, centrando nuestras políticas en el bienestar y progreso de todos los ciudadanos. \nActuamos como un enlace entre la sociedad y el gobierno, impulsando políticas públicas transparentes y basadas en las necesidades reales de la población. \nNos regimos por los principios de libertad, equidad y respeto a los derechos constitucionales, promoviendo valores como la solidaridad y la honestidad. \nNuestro objetivo es un Perú próspero y equitativo, donde cada ciudadano tenga oportunidades de desarrollo integral.','https://www.fuerzamoderna.org.pe/','activo','2025-11-04 01:04:37','2025-11-04 01:04:37');
/*!40000 ALTER TABLE `partidos_politicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestas`
--

DROP TABLE IF EXISTS `propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) DEFAULT NULL,
  `tipo_candidato` enum('presidencial','senador_nacional','senador_regional','diputado','parlamento_andino') DEFAULT NULL,
  `titulo` varchar(300) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `eje_tematico` varchar(100) DEFAULT NULL,
  `costo_estimado` decimal(15,2) DEFAULT NULL,
  `plazo_implementacion` varchar(100) DEFAULT NULL,
  `beneficiarios` text DEFAULT NULL,
  `archivo_url` varchar(500) DEFAULT NULL,
  `fecha_publicacion` date DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_tipo_candidato` (`tipo_candidato`),
  KEY `idx_categoria` (`categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestas`
--

LOCK TABLES `propuestas` WRITE;
/*!40000 ALTER TABLE `propuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `propuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyectos_realizados`
--

DROP TABLE IF EXISTS `proyectos_realizados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyectos_realizados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) DEFAULT NULL,
  `tipo_candidato` enum('presidencial','senador_nacional','senador_regional','diputado','parlamento_andino') DEFAULT NULL,
  `titulo` varchar(300) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `cargo_periodo` varchar(200) DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `monto_invertido` decimal(15,2) DEFAULT NULL,
  `beneficiarios` text DEFAULT NULL,
  `resultados` text DEFAULT NULL,
  `ubicacion` varchar(200) DEFAULT NULL,
  `evidencia_url` varchar(500) DEFAULT NULL,
  `imagen_url` varchar(500) DEFAULT NULL,
  `estado` enum('completado','en_ejecucion','suspendido') DEFAULT 'completado',
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_tipo_candidato` (`tipo_candidato`),
  KEY `idx_estado` (`estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyectos_realizados`
--

LOCK TABLES `proyectos_realizados` WRITE;
/*!40000 ALTER TABLE `proyectos_realizados` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyectos_realizados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultados_diputados`
--

DROP TABLE IF EXISTS `resultados_diputados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultados_diputados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) NOT NULL,
  `votos_preferenciales` int(11) DEFAULT 0,
  `votos_lista` int(11) DEFAULT 0,
  `votos_totales` int(11) DEFAULT 0,
  `es_elegido` tinyint(1) DEFAULT 0,
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_candidato_circunscripcion` (`candidato_id`,`circunscripcion_id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_circunscripcion` (`circunscripcion_id`),
  KEY `idx_elegido` (`es_elegido`),
  CONSTRAINT `resultados_diputados_ibfk_1` FOREIGN KEY (`candidato_id`) REFERENCES `candidatos_diputados` (`id`) ON DELETE CASCADE,
  CONSTRAINT `resultados_diputados_ibfk_2` FOREIGN KEY (`circunscripcion_id`) REFERENCES `circunscripciones` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultados_diputados`
--

LOCK TABLES `resultados_diputados` WRITE;
/*!40000 ALTER TABLE `resultados_diputados` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultados_diputados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultados_parlamento_andino`
--

DROP TABLE IF EXISTS `resultados_parlamento_andino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultados_parlamento_andino` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) NOT NULL,
  `votos_preferenciales` int(11) DEFAULT 0,
  `votos_lista` int(11) DEFAULT 0,
  `votos_totales` int(11) DEFAULT 0,
  `es_elegido` tinyint(1) DEFAULT 0,
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_candidato` (`candidato_id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_elegido` (`es_elegido`),
  CONSTRAINT `resultados_parlamento_andino_ibfk_1` FOREIGN KEY (`candidato_id`) REFERENCES `candidatos_parlamento_andino` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultados_parlamento_andino`
--

LOCK TABLES `resultados_parlamento_andino` WRITE;
/*!40000 ALTER TABLE `resultados_parlamento_andino` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultados_parlamento_andino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultados_presidenciales`
--

DROP TABLE IF EXISTS `resultados_presidenciales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultados_presidenciales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) DEFAULT NULL,
  `votos_obtenidos` int(11) DEFAULT 0,
  `porcentaje` decimal(5,2) DEFAULT 0.00,
  `actas_procesadas` int(11) DEFAULT 0,
  `actas_totales` int(11) DEFAULT 0,
  `vuelta` enum('primera','segunda') DEFAULT 'primera',
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_candidato_circunscripcion_vuelta` (`candidato_id`,`circunscripcion_id`,`vuelta`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_circunscripcion` (`circunscripcion_id`),
  CONSTRAINT `resultados_presidenciales_ibfk_1` FOREIGN KEY (`candidato_id`) REFERENCES `candidatos_presidenciales` (`id`) ON DELETE CASCADE,
  CONSTRAINT `resultados_presidenciales_ibfk_2` FOREIGN KEY (`circunscripcion_id`) REFERENCES `circunscripciones` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultados_presidenciales`
--

LOCK TABLES `resultados_presidenciales` WRITE;
/*!40000 ALTER TABLE `resultados_presidenciales` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultados_presidenciales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultados_senadores_nacionales`
--

DROP TABLE IF EXISTS `resultados_senadores_nacionales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultados_senadores_nacionales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) NOT NULL,
  `votos_preferenciales` int(11) DEFAULT 0,
  `votos_lista` int(11) DEFAULT 0,
  `votos_totales` int(11) DEFAULT 0,
  `es_elegido` tinyint(1) DEFAULT 0,
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_candidato` (`candidato_id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_elegido` (`es_elegido`),
  CONSTRAINT `resultados_senadores_nacionales_ibfk_1` FOREIGN KEY (`candidato_id`) REFERENCES `candidatos_senadores_nacionales` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultados_senadores_nacionales`
--

LOCK TABLES `resultados_senadores_nacionales` WRITE;
/*!40000 ALTER TABLE `resultados_senadores_nacionales` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultados_senadores_nacionales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultados_senadores_regionales`
--

DROP TABLE IF EXISTS `resultados_senadores_regionales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultados_senadores_regionales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidato_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) NOT NULL,
  `votos_preferenciales` int(11) DEFAULT 0,
  `votos_lista` int(11) DEFAULT 0,
  `votos_totales` int(11) DEFAULT 0,
  `es_elegido` tinyint(1) DEFAULT 0,
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_candidato_circunscripcion` (`candidato_id`,`circunscripcion_id`),
  KEY `idx_candidato` (`candidato_id`),
  KEY `idx_circunscripcion` (`circunscripcion_id`),
  KEY `idx_elegido` (`es_elegido`),
  CONSTRAINT `resultados_senadores_regionales_ibfk_1` FOREIGN KEY (`candidato_id`) REFERENCES `candidatos_senadores_regionales` (`id`) ON DELETE CASCADE,
  CONSTRAINT `resultados_senadores_regionales_ibfk_2` FOREIGN KEY (`circunscripcion_id`) REFERENCES `circunscripciones` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultados_senadores_regionales`
--

LOCK TABLES `resultados_senadores_regionales` WRITE;
/*!40000 ALTER TABLE `resultados_senadores_regionales` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultados_senadores_regionales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simulacro_votos`
--

DROP TABLE IF EXISTS `simulacro_votos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simulacro_votos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(8) NOT NULL,
  `nombre_completo` varchar(200) DEFAULT NULL,
  `tipo_eleccion` enum('presidencial','senador_nacional','senador_regional','diputado','parlamento_andino') NOT NULL,
  `candidato_id` int(11) NOT NULL,
  `circunscripcion_id` int(11) DEFAULT NULL,
  `mes_simulacro` int(11) NOT NULL,
  `anio_simulacro` int(11) NOT NULL,
  `fecha_voto` timestamp NULL DEFAULT current_timestamp(),
  `ip_address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`),
  KEY `idx_dni` (`dni`),
  KEY `idx_mes_anio` (`mes_simulacro`,`anio_simulacro`),
  KEY `idx_tipo_eleccion` (`tipo_eleccion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simulacro_votos`
--

LOCK TABLES `simulacro_votos` WRITE;
/*!40000 ALTER TABLE `simulacro_votos` DISABLE KEYS */;
INSERT INTO `simulacro_votos` VALUES (1,'46027896','ROXANA KARINA DELGADO CUELLAR','presidencial',1,NULL,11,2025,'2025-10-30 07:58:02','127.0.0.1');
/*!40000 ALTER TABLE `simulacro_votos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `nombre_completo` varchar(200) DEFAULT NULL,
  `rol` enum('admin','editor','visualizador') DEFAULT 'visualizador',
  `avatar_url` varchar(500) DEFAULT NULL,
  `estado` enum('activo','inactivo','suspendido') DEFAULT 'activo',
  `ultimo_acceso` timestamp NULL DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_rol` (`rol`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'juan','juan.aguirre.s@tecsup.edu.pe','$2b$12$HcihvMTvJf7vxRnG0HcbiO5yMd/LAniR8gaovyuDW/63JzfXXWk2m','Juan Aguirre','admin',NULL,'activo','2025-11-03 23:31:34','2025-10-29 22:19:34','2025-11-03 23:31:34');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_candidatos_presidenciales`
--

DROP TABLE IF EXISTS `vista_candidatos_presidenciales`;
/*!50001 DROP VIEW IF EXISTS `vista_candidatos_presidenciales`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_candidatos_presidenciales` AS SELECT 
 1 AS `id`,
 1 AS `partido_nombre`,
 1 AS `partido_siglas`,
 1 AS `partido_logo`,
 1 AS `presidente_completo`,
 1 AS `presidente_foto_url`,
 1 AS `vicepresidente1_completo`,
 1 AS `vicepresidente2_completo`,
 1 AS `numero_lista`,
 1 AS `estado`,
 1 AS `plan_gobierno_url`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_diputados`
--

DROP TABLE IF EXISTS `vista_diputados`;
/*!50001 DROP VIEW IF EXISTS `vista_diputados`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_diputados` AS SELECT 
 1 AS `id`,
 1 AS `partido_nombre`,
 1 AS `partido_siglas`,
 1 AS `circunscripcion_nombre`,
 1 AS `nombre_completo`,
 1 AS `foto_url`,
 1 AS `genero`,
 1 AS `profesion`,
 1 AS `posicion_lista`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_senadores_nacionales`
--

DROP TABLE IF EXISTS `vista_senadores_nacionales`;
/*!50001 DROP VIEW IF EXISTS `vista_senadores_nacionales`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_senadores_nacionales` AS SELECT 
 1 AS `id`,
 1 AS `partido_nombre`,
 1 AS `partido_siglas`,
 1 AS `partido_logo`,
 1 AS `nombre_completo`,
 1 AS `foto_url`,
 1 AS `genero`,
 1 AS `profesion`,
 1 AS `posicion_lista`,
 1 AS `numero_preferencial`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vista_candidatos_presidenciales`
--

/*!50001 DROP VIEW IF EXISTS `vista_candidatos_presidenciales`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_candidatos_presidenciales` AS select `cp`.`id` AS `id`,`pp`.`nombre` AS `partido_nombre`,`pp`.`siglas` AS `partido_siglas`,`pp`.`logo_url` AS `partido_logo`,concat(`cp`.`presidente_nombre`,' ',`cp`.`presidente_apellidos`) AS `presidente_completo`,`cp`.`presidente_foto_url` AS `presidente_foto_url`,concat(`cp`.`vicepresidente1_nombre`,' ',`cp`.`vicepresidente1_apellidos`) AS `vicepresidente1_completo`,concat(`cp`.`vicepresidente2_nombre`,' ',`cp`.`vicepresidente2_apellidos`) AS `vicepresidente2_completo`,`cp`.`numero_lista` AS `numero_lista`,`cp`.`estado` AS `estado`,`cp`.`plan_gobierno_url` AS `plan_gobierno_url` from (`candidatos_presidenciales` `cp` join `partidos_politicos` `pp` on(`cp`.`partido_id` = `pp`.`id`)) where `cp`.`estado` = 'aprobado' */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_diputados`
--

/*!50001 DROP VIEW IF EXISTS `vista_diputados`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_diputados` AS select `cd`.`id` AS `id`,`pp`.`nombre` AS `partido_nombre`,`pp`.`siglas` AS `partido_siglas`,`c`.`nombre` AS `circunscripcion_nombre`,concat(`cd`.`nombre`,' ',`cd`.`apellidos`) AS `nombre_completo`,`cd`.`foto_url` AS `foto_url`,`cd`.`genero` AS `genero`,`cd`.`profesion` AS `profesion`,`cd`.`posicion_lista` AS `posicion_lista`,`cd`.`estado` AS `estado` from ((`candidatos_diputados` `cd` join `partidos_politicos` `pp` on(`cd`.`partido_id` = `pp`.`id`)) join `circunscripciones` `c` on(`cd`.`circunscripcion_id` = `c`.`id`)) where `cd`.`estado` = 'aprobado' order by `c`.`nombre`,`pp`.`id`,`cd`.`posicion_lista` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_senadores_nacionales`
--

/*!50001 DROP VIEW IF EXISTS `vista_senadores_nacionales`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_senadores_nacionales` AS select `csn`.`id` AS `id`,`pp`.`nombre` AS `partido_nombre`,`pp`.`siglas` AS `partido_siglas`,`pp`.`logo_url` AS `partido_logo`,concat(`csn`.`nombre`,' ',`csn`.`apellidos`) AS `nombre_completo`,`csn`.`foto_url` AS `foto_url`,`csn`.`genero` AS `genero`,`csn`.`profesion` AS `profesion`,`csn`.`posicion_lista` AS `posicion_lista`,`csn`.`numero_preferencial` AS `numero_preferencial`,`csn`.`estado` AS `estado` from (`candidatos_senadores_nacionales` `csn` join `partidos_politicos` `pp` on(`csn`.`partido_id` = `pp`.`id`)) where `csn`.`estado` = 'aprobado' order by `pp`.`id`,`csn`.`posicion_lista` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-03 15:48:43
