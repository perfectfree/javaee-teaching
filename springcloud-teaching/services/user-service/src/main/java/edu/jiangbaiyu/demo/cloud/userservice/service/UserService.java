package edu.jiangbaiyu.demo.cloud.userservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.common.utils.PasswordEncoderUtil;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import edu.jiangbaiyu.demo.cloud.userservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务层，包含业务逻辑与事务控制
 * @author Robin
 * @date 2026/03/09
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    /**
     * 新增用户（带事务控制，校验邮箱唯一性）
     * @param user 用户信息
     * @return 保存后的用户
     */
    @Transactional(rollbackFor = Exception.class)
    public User addUser(User user) {
        // 校验邮箱唯一性
        long count = this.lambdaQuery().eq(User::getEmail, user.getEmail()).count();
        if (count > 0) {
            throw new BusinessException(400, "邮箱("+user.getEmail()+")已被注册");
        }
        // 加密密码
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoderUtil.encode(user.getPassword()));
        }
        this.save(user);
        return user;
    }

    /**
     * 根据ID查询用户（只读事务）
     * @param id 用户ID
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return this.getById(id);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        if (user.getId() == null) {
            throw new BusinessException(400, "用户ID不能为空");
        }
        // 如果更新了密码，则加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoderUtil.encode(user.getPassword()));
        }
        return this.updateById(user);
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        return this.removeById(id);
    }
}