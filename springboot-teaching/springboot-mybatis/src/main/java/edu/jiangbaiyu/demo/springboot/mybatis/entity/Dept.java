package edu.jiangbaiyu.demo.springboot.mybatis.entity;

/**
 * 部门实体类
 * @author Robin
 */
public class Dept {
    private Integer id;
    private String name;

    public Dept() {}

    public Dept(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Dept{id=" + id + ", name='" + name + "'}";
    }
}