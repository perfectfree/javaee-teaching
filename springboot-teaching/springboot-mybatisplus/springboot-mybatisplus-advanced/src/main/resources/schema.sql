DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT,
    email VARCHAR(50),
    deleted INT DEFAULT 0,           -- 逻辑删除字段
    create_time DATETIME,
    update_time DATETIME
);