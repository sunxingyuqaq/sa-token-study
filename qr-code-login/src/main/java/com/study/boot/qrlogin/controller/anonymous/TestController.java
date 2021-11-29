package com.study.boot.qrlogin.controller.anonymous;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * describe:
 *
 * @author admin
 * @date 2021/08/10 09:00
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public Dict test() {
        return Dict.create().set("code", "200").set("msg", "test");
    }

    @PostMapping("/json")
    public String testJson(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
        return "ok";
    }
}
