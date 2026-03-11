package edu.jiangbaiyu.demo.springboot.mybatis.entity;

/**
 * 用户实体类
 * @author Robin
 */
public class User {
    private Integer id;
    private String name;
    private String email;
    private Integer deptId;
    private Integer status;
    private Dept dept;  // 关联部门对象（用于关联查询）

    public User() {}

    public User(Integer id, String name, String email, Integer deptId, Integer status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deptId = deptId;
        this.status = status;
    }

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Dept getDept() { return dept; }
    public void setDept(Dept dept) { this.dept = dept; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", deptId=" + deptId +
                ", status=" + status +
                ", dept=" + dept +
                '}';
    }
}