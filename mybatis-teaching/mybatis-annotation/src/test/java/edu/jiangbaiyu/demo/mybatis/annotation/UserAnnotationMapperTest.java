package edu.jiangbaiyu.demo.mybatis.annotation;

import edu.jiangbaiyu.demo.mybatis.annotation.mapper.UserAnnotationMapper;
import edu.jiangbaiyu.demo.mybatis.annotation.pojo.User;
import edu.jiangbaiyu.demo.mybatis.annotation.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserAnnotationMapperTest {

    private SqlSession session;
    private UserAnnotationMapper mapper;

    @Before
    public void setUp() {
        session = MyBatisUtil.getSqlSession();
        mapper = session.getMapper(UserAnnotationMapper.class);
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testInsertUser() {
        User user = new User(null, "赵六", "zhaoliu@example.com");
        int rows = mapper.insertUser(user);
        assertEquals(1, rows);
        assertNotNull(user.getId());

        // 验证插入成功
        User inserted = mapper.selectUserById(user.getId());
        assertNotNull(inserted);
        assertEquals("赵六", inserted.getName());
    }

    @Test
    public void testSelectUserById() {
        // 先插入一条数据
        User user = new User(null, "测试", "test@example.com");
        mapper.insertUser(user);

        User found = mapper.selectUserById(user.getId());
        assertNotNull(found);
        assertEquals("测试", found.getName());
    }

    @Test
    public void testSelectAllUsers() {
        List<User> users = mapper.selectAllUsers();
        // 初始数据有两条（来自 schema.sql）
        assertTrue(users.size() >= 2);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(null, "原名字", "original@example.com");
        mapper.insertUser(user);

        user.setName("新名字");
        user.setEmail("new@example.com");
        int rows = mapper.updateUser(user);
        assertEquals(1, rows);

        User updated = mapper.selectUserById(user.getId());
        assertEquals("新名字", updated.getName());
        assertEquals("new@example.com", updated.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User user = new User(null, "待删除", "delete@example.com");
        mapper.insertUser(user);
        int id = user.getId();

        int rows = mapper.deleteUser(id);
        assertEquals(1, rows);

        User deleted = mapper.selectUserById(id);
        assertNull(deleted);
    }

    @Test
    public void testFullCrud() {
        // 插入
        User user = new User(null, "完整测试", "full@example.com");
        mapper.insertUser(user);
        assertNotNull(user.getId());

        // 查询
        User found = mapper.selectUserById(user.getId());
        assertEquals("完整测试", found.getName());

        // 更新
        found.setName("更新后");
        mapper.updateUser(found);
        User updated = mapper.selectUserById(found.getId());
        assertEquals("更新后", updated.getName());

        // 查询所有
        List<User> all = mapper.selectAllUsers();
        assertTrue(all.size() > 0);

        // 删除
        mapper.deleteUser(found.getId());
        assertNull(mapper.selectUserById(found.getId()));
    }
}