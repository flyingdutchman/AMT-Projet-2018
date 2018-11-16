DROP SCHEMA IF EXISTS amt_db;
CREATE SCHEMA amt_db;
USE amt_db;

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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  CONSTRAINT fk_user_id FOREIGN KEY (`owner`) REFERENCES user(`email`) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table `blacklist`
--

CREATE TABLE `blacklist` (
  `idBlackList` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fk_userEmail` varchar(45) NOT NULL, 
  PRIMARY KEY (`idBlackList`),
  CONSTRAINT fk_user_blacklist_id FOREIGN KEY (`fk_userEmail`) REFERENCES user(`email`) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
