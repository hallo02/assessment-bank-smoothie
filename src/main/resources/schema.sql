DROP TABLE IF EXISTS SMOOTHIES;
CREATE TABLE SMOOTHIES
(
    id            bigint NOT NULL AUTO_INCREMENT,
    name          varchar(50),
    slogan        varchar(500),
    carbohydrates varchar(50),
    fat           varchar(50),
    protein       varchar(50),
    PRIMARY KEY (id)
);