DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50)
);
INSERT INTO user (name, email) VALUES ('张三', 'zhangsan@example.com');
INSERT INTO user (name, email) VALUES ('李四', 'lisi@example.com');