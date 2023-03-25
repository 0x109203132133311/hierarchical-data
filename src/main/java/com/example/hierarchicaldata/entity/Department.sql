-- auto Generated on 2023-03-24
-- DROP TABLE IF EXISTS department;
CREATE TABLE department
(
    id      BIGINT(15) UNIQUE NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`  VARCHAR(50)       NOT NULL DEFAULT '' COMMENT 'name',
    lft     INT(11)           NOT NULL DEFAULT -1 COMMENT 'lft',
    rgt     INT(11)           NOT NULL DEFAULT -1 COMMENT 'rgt',
    `level` INT(11)           NOT NULL DEFAULT -1 COMMENT 'level',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'department';
