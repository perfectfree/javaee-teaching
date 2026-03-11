package edu.jiangbaiyu.demo.spring.aop.practice.service;

import edu.jiangbaiyu.demo.spring.aop.practice.annotation.LogExecution;
import edu.jiangbaiyu.demo.spring.aop.practice.annotation.RequireRole;
import org.springframework.stereotype.Service;

/**
 * 用户服务，演示自定义注解
 * @author Robin
 */
@Service
public class UserService {

    @LogExecution("查询用户")
    public String findUserById(Integer id) {
        System.out.println("【UserService】执行 findUserById，id=" + id);
        return "用户信息：User-" + id;
    }

    @LogExecution("创建用户")
    @RequireRole("ADMIN")
    public String createUser(String name) {
        System.out.println("【UserService】执行 createUser，name=" + name);
        return "用户创建成功：" + name;
    }

    @RequireRole({"ADMIN", "MANAGER"})
    public String deleteUser(Integer id) {
        System.out.println("【UserService】执行 deleteUser，id=" + id);
        return "用户删除成功：" + id;
    }

    // 内部调用演示：直接调用本类的另一个方法，AOP失效问题
    @LogExecution("内部调用示例")
    public String internalCallDemo(String name) {
        System.out.println("【UserService】internalCallDemo 开始");
        // 直接调用 createUser，不会触发AOP（因为不是通过代理对象调用）
        return createUser(name) + " (通过内部调用)";
    }

    // 解决方案：通过代理对象调用
    @LogExecution("内部调用修复")
    public String internalCallFixed(String name) {
        System.out.println("【UserService】internalCallFixed 开始");
        // 通过 ApplicationContext 获取代理对象，需要注入
        // 这里为了演示简单，在控制器中演示外部调用
        return "请参考控制器中的正确调用方式";
    }
}