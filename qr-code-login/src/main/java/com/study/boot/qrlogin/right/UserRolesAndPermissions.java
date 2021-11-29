package com.study.boot.qrlogin.right;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * describe:获取用户权限信息
 *
 * @author sxy
 * @date 2021/07/09 14:15
 */
@Component
public class UserRolesAndPermissions implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Arrays.asList("user:add", "user:del", "user:edit", "user:search");
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Arrays.asList("super-admin", "admin", "user", "manage");
    }
}
