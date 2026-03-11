package edu.jiangbaiyu.demo.maven.advanced;

import org.apache.commons.lang3.StringUtils;
//import org.h2.Driver; //runtime 范围：编译不需要，运行时需要
//import org.junit.Test; //test 范围: 用于单元测试框架，不会被打包进最终制品，在src/main/java 下编译会失败。
import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServlet; // provided 范围，编译存在，但运行时如果不在容器中会报错，此处仅用于演示编译通过

public class App {
    public static void main(String[] args) {
        System.out.println("Maven Advanced Features Demo");

        // 使用 commons-lang3 (compile 范围)
        String message = "  Hello Maven  ";
        System.out.println("Trimmed: '" + StringUtils.trim(message) + "'");

        // 使用 javax.servlet-api (provided 范围) - 仅编译，如果取消注释，运行时需要容器
//         System.out.println("Servlet class: " + HttpServlet.class.getName());
    }

    public static String reverse(String input) {
        return StringUtils.reverse(input);
    }
}