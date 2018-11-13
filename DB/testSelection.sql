SELECT `email`, `password`, `firstName`, `lastName`, `right` FROM `user`
WHERE `right`='DEVELOPER';

SELECT `idApplication`, `name`, `description`, `api_key`, `api_secret` FROM `application`;