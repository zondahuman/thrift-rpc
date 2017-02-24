package com.abin.lee.thrift.rpc.service.model;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 17-2-24
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private String name;
    private Integer age;
    private String address;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
