DROP SCHEMA IF EXISTS `amt_db`;
CREATE SCHEMA `amt_db`;
USE `amt_db`;

-- DROP TABLE IF EXISTS `userApplication`;
DROP TABLE IF EXISTS `application`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `blacklist`;

--
-- Table `user`
--

CREATE TABLE `user` (
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `right` varchar(9) DEFAULT 'DEVELOPER',
  PRIMARY KEY (`email`)
);

--
-- Table `application`
--

CREATE TABLE `application` (
  `idApplication` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `api_key` varchar(45) NOT NULL,
  `api_secret` varchar(45) NOT NULL,
  `owner` varchar(45) NOT NULL,
  PRIMARY KEY (`idApplication`),
  CONSTRAINT FK_UserId FOREIGN KEY (`owner`) REFERENCES User(`email`)
);

--
-- Table `userApplication`
--

-- CREATE TABLE `userApplication` (
--   `idApk` int(10) unsigned NOT NULL,
--   `idUser` varchar(45) NOT NULL,
--   CONSTRAINT PK_UserApk PRIMARY KEY (idApk, idUser),
--   CONSTRAINT FK_UserId FOREIGN KEY (idUser) REFERENCES User(`email`),
--   CONSTRAINT FK_ApkId FOREIGN KEY (idApk) REFERENCES Application(`idApplication`)
-- );

--
-- Table `blacklist`
--

CREATE TABLE `blacklist` (
  `idBlackList` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fk_userEmail` varchar(45) NOT NULL, 
  PRIMARY KEY (`idBlackList`),
  CONSTRAINT FK_UserBlacklistId FOREIGN KEY (`fk_userEmail`) REFERENCES User(`email`)
);
