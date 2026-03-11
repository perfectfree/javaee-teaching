package edu.jiangbaiyu.demo.mybatis.advanced;

import edu.jiangbaiyu.demo.mybatis.advanced.mapper.UserMapper;
import edu.jiangbaiyu.demo.mybatis.advanced.pojo.User;
import edu.jiangbaiyu.demo.mybatis.advanced.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {

    private SqlSession session;
    private UserMapper mapper;

    @Before
    public void setUp() {
        session = MyBatisUtil.getSqlSession();
        mapper = session.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
    }

    // 测试基础 CRUD
    @Test
    public void testInsertAndSelect() {
        User user = new User(null, "新用户", "new@example.com", 1, 1);
        int rows = mapper.insertUser(user);
        assertEquals(1, rows);
        assertNotNull(user.getId());

        User found = mapper.selectUserById(user.getId());
        assertNotNull(found);
        assertEquals("新用户", found.getName());
        assertEquals(1, found.getDeptId().intValue());
        assertEquals(1, found.getStatus().intValue());
    }

    @Test
    public void testUpdate() {
        User user = new User(null, "待更新", "update@example.com", 2, 0);
        mapper.insertUser(user);
        int id = user.getId();

        user.setName("已更新");
        user.setEmail("updated@example.com");
        user.setDeptId(1);
        user.setStatus(1);
        int rows = mapper.updateUser(user);
        assertEquals(1, rows);

        User updated = mapper.selectUserById(id);
        assertEquals("已更新", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals(1, updated.getDeptId().intValue());
        assertEquals(1, updated.getStatus().intValue());
    }

    @Test
    public void testDelete() {
        User user = new User(null, "待删除", "delete@example.com", 1, 0);
        mapper.insertUser(user);
        int id = user.getId();

        int rows = mapper.deleteUser(id);
        assertEquals(1, rows);
        assertNull(mapper.selectUserById(id));
    }

    @Test
    public void testSelectAllUsers() {
        List<User> users = mapper.selectAllUsers();
        // 初始数据有3条（来自 schema.sql）
        assertTrue(users.size() >= 3);
    }

    // 测试动态SQL多条件查询
    @Test
    public void testSearchUsers() {
        // 按姓名模糊查询
        List<User> users = mapper.searchUsers("张", null, null, null);
        assertEquals(1, users.size());
        assertEquals("张三", users.get(0).getName());

        // 按邮箱精确查询
        users = mapper.searchUsers(null, "lisi@example.com", null, null);
        assertEquals(1, users.size());
        assertEquals("李四", users.get(0).getName());

        // 按状态查询
        users = mapper.searchUsers(null, null, 1, null);
        assertTrue(users.size() >= 2); // 张三、李四状态为1

        // 按部门查询
        users = mapper.searchUsers(null, null, null, 1);
        assertTrue(users.size() >= 2); // 张三、王五在技术部

        // 组合条件：姓名含'王'且状态为0
        users = mapper.searchUsers("王", null, 0, null);
        assertEquals(1, users.size());
        assertEquals("王五", users.get(0).getName());
    }

    // 测试批量删除
    @Test
    public void testDeleteUsersByIds() {
        // 先插入几个用户
        User u1 = new User(null, "批量1", "batch1@example.com", 1, 1);
        User u2 = new User(null, "批量2", "batch2@example.com", 2, 0);
        mapper.insertUser(u1);
        mapper.insertUser(u2);
        int id1 = u1.getId();
        int id2 = u2.getId();

        List<Integer> ids = Arrays.asList(id1, id2);
        int rows = mapper.deleteUsersByIds(ids);
        assertEquals(2, rows);

        assertNull(mapper.selectUserById(id1));
        assertNull(mapper.selectUserById(id2));
    }

    // 测试关联查询
    @Test
    public void testSelectUsersWithDept() {
        List<User> users = mapper.selectUsersWithDept();
        // 初始数据中每个用户应有关联部门
        for (User user : users) {
            assertNotNull(user.getDept());
            System.out.println(user.getName() + " -> " + user.getDept().getName());
        }
        // 至少应该有3条记录
        assertTrue(users.size() >= 3);
    }
}