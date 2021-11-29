package com.study.boot.qrlogin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 12870
 */
@Controller
@RequestMapping("user")
public class QrLoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 视图跳转到login页面
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 创建二维码
     *
     * @return
     */
    @PostMapping("qrcode/create")
    @ResponseBody
    public Map<String, Object> qrCreat() {
        String scheme = "http";
        File file = FileUtil.file("d:/qrcode.jpg");
        QrCodeUtil.generate(scheme + "://192.168.107.96:9098/user/qrcode?t=" + DateUtil.current(),
                200, 200, file);
        String encode = Base64.encode(file);
        JSONObject jsonObject = new JSONObject();
        String s = IdUtil.simpleUUID();
        jsonObject.put("qrcodeId", s);
        jsonObject.put("loginId", "sxy");
        jsonObject.put("isLogin", "0");
        jsonObject.put("qrcodeImgUrl", "data:image/jpg;base64," + encode);
        stringRedisTemplate.opsForHash().putAll(s, jsonObject);
        stringRedisTemplate.expire(s, 60, TimeUnit.SECONDS);
        return getResult("200", "ok", jsonObject);
    }

    /**
     * 判断是否登录了
     *
     * @param qrcodeId
     * @return
     */
    @PostMapping("qrcode/isLogin")
    @ResponseBody
    public Map<String, Object> isLogin(String qrcodeId) {
        String isLogin = (String) stringRedisTemplate.opsForHash().get(qrcodeId, "isLogin");
        if ("1".equals(isLogin)) {
            return getResult("200", "登录成功！", null);
        }
        return getResult("201", "还没有确认登录。。。", null);
    }

    /**
     * 模拟登录
     *
     * @param qrcodeId
     * @param agree
     * @return
     */
    @PostMapping("/qrcode/login")
    @ResponseBody
    public Map<String, Object> qrLogin(String qrcodeId, String agree) {
        String loginId = (String) stringRedisTemplate.opsForHash().get(qrcodeId, "loginId");
        stringRedisTemplate.opsForHash().put(qrcodeId, "isLogin", agree);
        StpUtil.login(loginId, "PC");
        String tokenValue = StpUtil.getTokenValue();
        return getResult("200", "登录成功！", tokenValue);
    }

    /**
     * data模型
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    private Map<String, Object> getResult(String code, String msg, Object data) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
