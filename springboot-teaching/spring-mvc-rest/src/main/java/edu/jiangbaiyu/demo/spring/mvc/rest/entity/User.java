package edu.jiangbaiyu.demo.spring.mvc.rest.entity;

/**
 * 用户实体类
 * @author Robin
 */
public class User {
    private Integer id;
    private String name;
    private String email;
    private Integer status; // 0-禁用，1-启用

    public User() {}

    public User(Integer id, String name, String email, Integer status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', status=" + status + "}";
    }
}