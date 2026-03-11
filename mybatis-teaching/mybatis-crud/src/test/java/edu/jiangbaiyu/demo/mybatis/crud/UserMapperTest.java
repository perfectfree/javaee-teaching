package edu.jiangbaiyu.demo.mybatis.crud;

import edu.jiangbaiyu.demo.mybatis.crud.mapper.UserMapper;
import edu.jiangbaiyu.demo.mybatis.crud.pojo.User;
import edu.jiangbaiyu.demo.mybatis.crud.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {
    @Test
    public void testCrud() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // insert
            User newUser = new User(null, "王五", "wangwu@example.com");
            int rows = mapper.insertUser(newUser);
            assertEquals(1, rows);
            assertNotNull(newUser.getId());

            // select by id
            User user = mapper.selectUserById(newUser.getId());
            assertEquals("王五", user.getName());

            // update
            user.setName("王五改");
            mapper.updateUser(user);
            User updated = mapper.selectUserById(user.getId());
            assertEquals("王五改", updated.getName());

            // select all
            List<User> users = mapper.selectAllUsers();
            assertTrue(users.size() >= 3);

            // delete
            mapper.deleteUser(user.getId());
            assertNull(mapper.selectUserById(user.getId()));
        }
    }
}