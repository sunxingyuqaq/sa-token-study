package com.study.boot.qrlogin.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * describe:
 *
 * @author admin
 * @date 2021/07/12 14:28
 */
@RequestMapping("user")
@Controller
public class UserController {

    /**
     * 视图跳转到index页面
     *
     * @return
     */
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("loginId", StpUtil.getLoginIdAsString());
        model.addAttribute("token", StpUtil.getTokenValue());
        return "index";
    }
}
