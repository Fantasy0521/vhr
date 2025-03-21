package org.javaboy.vhr.model.vo;

import org.javaboy.vhr.model.OpLog;

public class OpLogVo extends OpLog {

    //操作人名称
    private String name;

    //操作人地址
    private String address;

    //操作人账号
    private String username;

    //操作人头像
    private String userface;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }
}
