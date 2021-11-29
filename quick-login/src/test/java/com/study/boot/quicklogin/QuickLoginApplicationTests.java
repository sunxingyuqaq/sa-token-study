package com.study.boot.quicklogin;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class QuickLoginApplicationTests {

    @Test
    void contextLoads() {
        HttpUtil.downloadFile("http://localhost:8098/epoint-web/rest/readpictureaction/getSignPicture?isCommondto=true&cliengGuid=45f0c5f9-cad2-49e6-887d-b38dfcbc23de","d:/test/test1.png");
        HttpUtil.downloadFile("http://localhost:8098/epoint-web/rest/readpictureaction/getSignPicture?isCommondto=true&cliengGuid=45f0c5f9-cad2-49e6-887d-b38dfcbc23de","d:/test/test2.png");
    }

}
