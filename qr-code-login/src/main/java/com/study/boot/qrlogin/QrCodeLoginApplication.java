package com.study.boot.qrlogin;

import cn.dev33.satoken.SaManager;
import cn.hutool.core.lang.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * describe: main class run
 *
 * @author sxy
 * @date 2021/07/09 14:13
 */
@SpringBootApplication
public class QrCodeLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrCodeLoginApplication.class, args);
        Console.log(SaManager.getConfig());
    }
}
