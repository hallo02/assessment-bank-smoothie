DROP TABLE IF EXISTS Smoothies;
CREATE TABLE Smoothies
(
    id        bigint NOT NULL AUTO_INCREMENT,
    name      varchar(255),
    img       varchar(255),
    nutrition varchar(255),
    PRIMARY KEY (id)
);