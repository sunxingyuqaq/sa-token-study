package com.study.boot.qrlogin.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * describe:路由权限配置
 *
 * @author sxy
 * @date 2021/07/09 14:18
 */
@Configuration
public class QrLoginConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {

            SaRouter.match(Collections.singletonList("/**"), Arrays.asList("/", "/user/login", "/user/logout", "/user/qrcode/**", "/qr-res/**", "/favicon.ico", "/error", "/api/test", "/api/json"), StpUtil::checkLogin);

            SaRouter.match("/admin/**", () -> StpUtil.checkRoleOr("admin", "super-admin"));
            SaRouter.match("/admin/**", () -> StpUtil.checkPermission("admin"));
            SaRouter.match("/goods/**", () -> StpUtil.checkPermission("goods"));
            SaRouter.match("/orders/**", () -> StpUtil.checkPermission("orders"));
            SaRouter.match("/notice/**", () -> StpUtil.checkPermission("notice"));
            SaRouter.match("/comment/**", () -> StpUtil.checkPermission("comment"));

        })).addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }
}
