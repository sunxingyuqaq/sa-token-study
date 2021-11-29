package com.study.boot.quicklogin.controller;

import cn.dev33.satoken.stp.StpUtil;

/**
 * describe:
 *
 * @author admin
 * @date 2021/09/18 10:55
 */
public abstract class BaseController {

    public long getLoginId() {
        return StpUtil.getLoginIdAsLong();
    }

    public String getUserName() {
        return getLoginId() + "-name";
    }


}