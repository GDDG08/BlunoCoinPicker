package com.zzh.bamboobook.data.db;

/**
 * Created on 2018/5/26-13-23.
 */
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 数据原型
 * 这里要添加数据的注解，就可以对应数据库中的表和字段，
 * 注解也是xUtils中的工具类完成的
 */
@Table(name = "table_user")//注释表名
public class User {


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Column(name = "username")//注释列名
    private String username;

    @Column(name = "id", isId = true, autoGen = true)//注释列名主键，主动增长
    private int id;

    @Column(name = "password")//注释列名
    private String password;

    @Column(name = "age")//注释列名
    private int age;

    @Column(name = "sex")//注释列名
    private String sex;


    //必须有空参的构造方法和set与get


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
