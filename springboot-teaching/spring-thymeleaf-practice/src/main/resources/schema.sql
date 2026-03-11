DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50),
    dept_id INT,
    status INT
);

INSERT INTO user (name, email, dept_id, status) VALUES
('张三', 'zhangsan@example.com', 1, 1),
('李四', 'lisi@example.com', 2, 1),
('王五', 'wangwu@example.com', 1, 0);