package com.study.boot.quicklogin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.study.boot.base.CodeBaseException;
import com.study.boot.base.CommonResponseEnum;
import com.study.boot.base.Response;
import com.study.boot.quicklogin.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.study.boot.base.CommonResponseEnum.INTERNAL_ERROR;

/**
 * describe: HomeController 测试接口
 *
 * @author admin
 * @date 2021/09/18 11:04
 */
@Slf4j
@RestController
@RequestMapping("api")
public class HomeController extends BaseController {

    /**
     * test
     *
     * @param name name
     * @return s
     */
    @GetMapping("/test")
    public String test(String name) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        log.info("{}", loginIdAsString);
        return name + " ok";
    }

    /**
     * hello1 测试接口
     *
     * @return Response
     */
    @GetMapping("hello1")
    public Response<String> hello1(HttpServletRequest request) {
        Response<String> response = new Response<>();
        try {
            response.setCode(200);
            response.setDesc("请求成功");
            response.setData("hello");
            response.setPath(request.getRequestURI());
        }
        catch (Exception e) {
            log.error("hello1方法请求异常", e);
            response.setCode(500);
            response.setDesc("请求异常:" + e.getMessage());
        }
        finally {
            log.info("执行controller的finally结构");
        }
        return response;
    }

    /**
     * hello2 测试接口2
     *
     * @param ex e
     * @return Response
     */
    @GetMapping("hello2")
    public Response<String> hello2(@RequestParam("ex") String ex) {
        switch (ex) {
            case "ex1":
                throw new CodeBaseException(CommonResponseEnum.USER_NOT_FOUND, "用户信息不存在");
            case "ex2":
                throw new CodeBaseException(CommonResponseEnum.MENU_NOT_FOUND, "菜单信息不存在");
            case "ex3":
                throw new CodeBaseException(CommonResponseEnum.ILLEGAL_ARGUMENT, "请求参数异常");
            case "ex4":
                throw new CodeBaseException(CommonResponseEnum.DATA_NOT_FOUND, "数据不存在");
            default:
                break;
        }
        throw new CodeBaseException(INTERNAL_ERROR, new Object[]{ex}, "请求异常", new RuntimeException("运行时异常信息"));
    }

    /**
     * map
     *
     * @param json json
     * @return s
     */
    @PostMapping("json")
    public JSON map(@RequestBody String json) {
        JSON parse = JSONUtil.parse(json);
        parse.putByPath("test", "sxy");
        return parse;
    }

    /**
     * 创建用户信息
     *
     * @param model user
     * @return d
     */
    @PostMapping("user")
    public Dict createUser(@RequestBody UserModel model) {
        Dict dict = Dict.create();
        dict.put("msg", model.getName() + " 创建成功！");
        return dict;
    }
}
