-- 如果表存在则删除（注意顺序，先删子表）
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS dept;

-- 创建部门表
CREATE TABLE dept (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- 创建用户表
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50),
    dept_id INT,
    status INT,
    FOREIGN KEY (dept_id) REFERENCES dept(id)
);

-- 插入部门数据
INSERT INTO dept (name) VALUES ('技术部'), ('市场部'), ('人事部');

-- 插入用户数据
INSERT INTO user (name, email, dept_id, status) VALUES
('张三', 'zhangsan@example.com', 1, 1),
('李四', 'lisi@example.com', 2, 1),
('王五', 'wangwu@example.com', 1, 0),
('赵六', 'zhaoliu@example.com', 3, 1);