package edu.jiangbaiyu.demo.spring.aop.basic.service;

import org.springframework.stereotype.Service;

/**
 * 产品服务类（无接口，演示 CGLIB 代理）
 * @author Robin
 */
@Service
public class ProductService {

    public String findProductById(Integer id) {
        System.out.println("【ProductService】执行 findProductById，id=" + id);
        return "Product-" + id;
    }

    public void deleteProduct(Integer id) {
        System.out.println("【ProductService】执行 deleteProduct，id=" + id);
    }
}