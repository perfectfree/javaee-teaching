package edu.jiangbaiyu.demo.mybatis.comprehensive;

import edu.jiangbaiyu.demo.mybatis.comprehensive.mapper.UserMapper;
import edu.jiangbaiyu.demo.mybatis.comprehensive.pojo.User;
import edu.jiangbaiyu.demo.mybatis.comprehensive.util.MyBatisUtil;
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

    // 测试注解版CRUD
    @Test
    public void testAnnotationCrud() {
        // 插入
        User user = new User(null, "注解用户", "anno@example.com", 1, 1);
        int rows = mapper.insertUser(user);
        assertEquals(1, rows);
        assertNotNull(user.getId());

        // 查询
        User found = mapper.selectUserById(user.getId());
        assertEquals("注解用户", found.getName());

        // 更新
        found.setName("注解更新");
        found.setEmail("updated@example.com");
        mapper.updateUser(found);
        User updated = mapper.selectUserById(found.getId());
        assertEquals("注解更新", updated.getName());

        // 查询所有
        List<User> all = mapper.selectAllUsers();
        assertTrue(all.size() >= 4); // 初始4条 + 1条

        // 删除
        mapper.deleteUser(found.getId());
        assertNull(mapper.selectUserById(found.getId()));
    }

    // 测试动态多条件查询
    @Test
    public void testSearchUsers() {
        List<User> users = mapper.searchUsers("张", null, null, null);
        assertEquals(1, users.size());
        assertEquals("张三", users.get(0).getName());

        users = mapper.searchUsers(null, "lisi@example.com", null, null);
        assertEquals(1, users.size());
        assertEquals("李四", users.get(0).getName());

        users = mapper.searchUsers(null, null, 1, null);
        assertTrue(users.size() >= 3); // 张三、李四、赵六状态为1

        users = mapper.searchUsers(null, null, null, 1);
        assertTrue(users.size() >= 2); // 张三、王五在技术部
    }

    // 测试批量删除
    @Test
    public void testDeleteUsersByIds() {
        // 插入两条测试数据
        User u1 = new User(null, "批量A", "batchA@example.com", 2, 0);
        User u2 = new User(null, "批量B", "batchB@example.com", 3, 1);
        mapper.insertUser(u1);
        mapper.insertUser(u2);
        int id1 = u1.getId();
        int id2 = u2.getId();

        int rows = mapper.deleteUsersByIds(Arrays.asList(id1, id2));
        assertEquals(2, rows);

        assertNull(mapper.selectUserById(id1));
        assertNull(mapper.selectUserById(id2));
    }

    // 测试关联查询
    @Test
    public void testSelectUsersWithDept() {
        List<User> users = mapper.selectUsersWithDept();
        assertTrue(users.size() >= 4);
        for (User user : users) {
            assertNotNull(user.getDept());
            System.out.println(user.getName() + " -> " + user.getDept().getName());
        }
    }
}