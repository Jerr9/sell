package com.o2ii.sell.dataobject;

import lombok.Data;

@Data
public class User {
    private String uid;
    private String openId;
    private String mobile;
    private Integer sex;
    private Integer age;
    private String password;
    private String nickname;
}
