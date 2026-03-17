-- 创建用户表（包含密码和角色字段）
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL,            -- 角色，如 'ROLE_USER,ROLE_ADMIN'
    status INT DEFAULT 1,                    -- 1启用，0禁用
    email VARCHAR(50)
);

-- 插入测试用户，密码使用 BCrypt 加密（原始密码：123456）
INSERT INTO sys_user (username, password, roles, status, email) VALUES
('user', '$2a$10$/Uq7KXOqPyTsrlnggsdtQ.Gef7ojUVRSqqPRBbxlpdKzRnBnpajxK', 'ROLE_USER', 1, 'user@example.com'),
('admin', '$2a$10$/Uq7KXOqPyTsrlnggsdtQ.Gef7ojUVRSqqPRBbxlpdKzRnBnpajxK', 'ROLE_USER,ROLE_ADMIN', 1, 'admin@example.com');

--注意：上面密码加密字符串需要实际生成，可以使用 BCryptPasswordEncoder 生成。
--为简化，可以在启动时通过配置类插入。但为了独立运行，我们使用一个固定的加密串（对应原始密码"123456"）。可以使用在线生成或代码生成。