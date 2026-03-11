package edu.jiangbaiyu.demo.spring.bean.scope.controller;

import edu.jiangbaiyu.demo.spring.bean.scope.bean.LifecycleBean;
import edu.jiangbaiyu.demo.spring.bean.scope.bean.PrototypeBean;
import edu.jiangbaiyu.demo.spring.bean.scope.bean.SingletonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作用域演示控制器
 * @author Robin
 */
@RestController
public class ScopeController {

    @Autowired
    private SingletonBean singletonBean;      // 单例 Bean（同一个实例）

    // 原型 Bean 的注入需要特殊处理，这里演示通过 ApplicationContext 获取
    @Autowired
    private org.springframework.context.ApplicationContext context;

    @Autowired
    private LifecycleBean lifecycleBean;       // 测试生命周期回调

    /**
     * 测试单例 Bean：多次访问，count 累加，hashCode 不变
     */
    @GetMapping("/scope/singleton")
    public String testSingleton() {
        return "Singleton: " + singletonBean.incrementAndGet() + " - " + singletonBean.getInfo();
    }

    /**
     * 测试原型 Bean：每次从容器中获取新实例
     */
    @GetMapping("/scope/prototype")
    public String testPrototype() {
        PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
        return "Prototype: " + prototypeBean.incrementAndGet() + " - " + prototypeBean.getInfo();
    }

    /**
     * 测试原型 Bean 的另一种方式：使用 Lookup 方法注入（推荐，避免依赖 ApplicationContext）
     */
    @GetMapping("/scope/prototype2")
    public String testPrototype2() {
        PrototypeBean prototypeBean = getPrototypeBean();
        return "Prototype (lookup): " + prototypeBean.incrementAndGet() + " - " + prototypeBean.getInfo();
    }

    @Lookup  // Spring 会动态实现该方法，每次返回新的 PrototypeBean 实例
    public PrototypeBean getPrototypeBean() {
        // 方法体将被 Spring 覆盖，实际不会执行
        return null;
    }

    /**
     * 触发 LifecycleBean 的业务方法，观察生命周期回调（启动和关闭时打印）
     */
    @GetMapping("/scope/lifecycle")
    public String testLifecycle() {
        lifecycleBean.doWork();
        return "Lifecycle method executed, check console logs.";
    }
}