package edu.jiangbaiyu.demo.maven;

import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello from Maven project!");
        String message = "  Maven + MyBatis  ";
        System.out.println("Trimmed: '" + StringUtils.trim(message) + "'");
    }

    public static String reverse(String input) {
        return StringUtils.reverse(input);
    }
}