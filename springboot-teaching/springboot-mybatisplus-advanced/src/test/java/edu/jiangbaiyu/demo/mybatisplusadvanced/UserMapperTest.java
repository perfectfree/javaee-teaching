package edu.jiangbaiyu.demo.mybatisplusadvanced;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.jiangbaiyu.demo.mybatisplusadvanced.entity.User;
import edu.jiangbaiyu.demo.mybatisplusadvanced.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 单元测试类
 * 验证 MyBatis-Plus 高级特性
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User(null, "测试用户", 25, "test@example.com");
        int rows = userMapper.insert(user);
        assertThat(rows).isEqualTo(1);
        assertThat(user.getId()).isNotNull();
        // 验证自动填充
        assertThat(user.getCreateTime()).isNotNull();
        assertThat(user.getUpdateTime()).isNotNull();
    }

    @Test
    public void testConditionQuery() {
        // 先插入数据
        userMapper.insert(new User(null, "张三", 20, "zhangsan@example.com"));
        userMapper.insert(new User(null, "李四", 30, "lisi@example.com"));

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(User::getAge, 25);
        List<User> users = userMapper.selectList(wrapper);
        assertThat(users).allMatch(u -> u.getAge() >= 25);
    }

    @Test
    public void testPage() {
        // 插入10条数据
        for (int i = 0; i < 10; i++) {
            userMapper.insert(new User(null, "用户" + i, 20 + i, "user" + i + "@example.com"));
        }

        Page<User> page = new Page<>(2, 3); // 第2页，每页3条
        Page<User> result = userMapper.selectPage(page, null);
        assertThat(result.getRecords()).hasSize(3);
        assertThat(result.getTotal()).isGreaterThanOrEqualTo(10);
    }

    @Test
    public void testLogicDelete() {
        User user = new User(null, "待删除", 40, "delete@example.com");
        userMapper.insert(user);
        Long id = user.getId();

        // 逻辑删除
        int rows = userMapper.deleteById(id);
        assertThat(rows).isEqualTo(1);

        // 直接查询应该为null（自动过滤）
        User found = userMapper.selectById(id);
        assertThat(found).isNull();

        // 使用条件构造器且不自动过滤逻辑删除（演示）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        List<User> list = userMapper.selectList(wrapper); // 默认会加上deleted=0条件，所以查不到
        assertThat(list).isEmpty();

        // 如果要查询所有包括已删除的，需要wrapper加上条件忽略逻辑删除
        // MyBatis-Plus 提供了两种方式：使用 @InterceptorIgnore(tenantLine = "true") 或者在构造器中手动忽略
        // 这里使用构造器手动添加条件覆盖逻辑删除过滤（不推荐，仅测试）
        LambdaQueryWrapper<User> wrapperAll = new LambdaQueryWrapper<>();
        wrapperAll.eq(User::getId, id);
        // 由于MP会自动追加 deleted=0，所以仍然查不到，需要清除wrapper中的逻辑删除条件？实际上MP是全局过滤，不能简单清除。
        // 为了测试，我们可以使用 userMapper.selectList(new LambdaQueryWrapper<>()) 查询全部，包括逻辑删除的？
        // 在配置中逻辑删除是全局的，selectList 也会自动过滤，除非在Mapper方法上使用 @InterceptorIgnore。
        // 这里不深入，可以在Service中演示通过自定义SQL查询。
        // 简单起见，我们只验证删除后selectById为空即可。
    }
}