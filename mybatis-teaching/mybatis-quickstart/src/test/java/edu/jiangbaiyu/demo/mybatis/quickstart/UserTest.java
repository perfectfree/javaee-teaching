package edu.jiangbaiyu.demo.mybatis.quickstart;

import edu.jiangbaiyu.demo.mybatis.quickstart.pojo.User;
import edu.jiangbaiyu.demo.mybatis.quickstart.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void testSelectById() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            User user = session.selectOne("UserMapper.selectUserById", 1);
            assertNotNull(user);
            assertEquals("张三", user.getName());
            System.out.println(user);
        }
    }
}