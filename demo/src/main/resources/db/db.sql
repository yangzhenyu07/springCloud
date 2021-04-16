set SQL_SAFE_UPDATES = 0;
-- ¡∑œ∞
CREATE TABLE test (
  id bigint(200) NOT NULL AUTO_INCREMENT,
  age varchar(200) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ¿÷π€À¯
CREATE TABLE test_version (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version varchar(50) DEFAULT NULL COMMENT '∞Ê±æ∫≈',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

INSERT INTO testdb.test_version(id, version) VALUES (1, '0');


set SQL_SAFE_UPDATES = 1;