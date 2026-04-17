package edu.jiangbaiyu.demo.userservice.service;

import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.userservice.App;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import edu.jiangbaiyu.demo.cloud.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    Random rand = new Random();

    @Test
//    @Rollback(false)
    @Commit
    void testAddUser_Success() {
        User user = new User();
        user.setName("张三15"+rand.nextInt(30));
        user.setPassword("123456");
        user.setEmail(user.getName()+"@example.com");
        user.setAge(15);

        User saved = userService.addUser(user);
        assertNotNull(saved.getId());
        assertEquals(user.getName(), saved.getName());
        assertNotNull(saved.getPassword());
        assertNotEquals("123456", saved.getPassword()); // 密码应加密
    }

    @Test
    void testAddUser_DuplicateEmail() {
        User user1 = new User();
        user1.setName("李四");
        user1.setPassword("123456");
        user1.setEmail("lisi@example.com");
        userService.addUser(user1);

        User user2 = new User();
        user2.setName("李四2");
        user2.setPassword("123456");
        user2.setEmail("lisi@example.com");
        assertThrows(BusinessException.class, () -> userService.addUser(user2));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setName("王五");
        user.setPassword("123456");
        user.setEmail("wangwu@example.com");
        User saved = userService.addUser(user);

        User found = userService.getUserById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getName(), found.getName());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("赵六");
        user.setPassword("123456");
        user.setEmail("zhaoliu@example.com");
        User saved = userService.addUser(user);

        saved.setAge(30);
        boolean updated = userService.updateUser(saved);
        assertTrue(updated);

        User updatedUser = userService.getUserById(saved.getId());
        assertEquals(30, updatedUser.getAge());
    }
}