package com.study.boot.quicklogin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/25 14:46
 */
@Data
public class UserModel implements Serializable {

    /**
     * id 用户id
     */
    private Long id;
    /**
     * name 用户名
     */
    private String name;
    /**
     * sex  性别
     */
    private String sex;
    /**
     * age 年龄
     */
    private Integer age;
    /**
     * address 家庭地址
     */
    private String address;
}
