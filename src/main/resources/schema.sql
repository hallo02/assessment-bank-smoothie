DROP TABLE IF EXISTS Smoothies;
CREATE TABLE Smoothies
(
    id            bigint NOT NULL AUTO_INCREMENT,
    name          varchar(255),
    img           varchar(255),
    carbohydrates varchar(255),
    fat           varchar(255),
    protein       varchar(255),
    PRIMARY KEY (id)
);