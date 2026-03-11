DROP TABLE IF EXISTS dept;
CREATE TABLE dept (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50),
    dept_id INT,
    status INT,
    FOREIGN KEY (dept_id) REFERENCES dept(id)
);

INSERT INTO dept (name) VALUES ('技术部'), ('市场部'), ('人事部');

INSERT INTO user (name, email, dept_id, status) VALUES
('张三', 'zhangsan@example.com', 1, 1),
('李四', 'lisi@example.com', 2, 1),
('王五', 'wangwu@example.com', 1, 0),
('赵六', 'zhaoliu@example.com', 3, 1);