package com.study.boot.quicklogin.controller;

import cn.hutool.json.JSONUtil;
import com.study.boot.quicklogin.config.websocket.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/26 13:44
 */
@Controller
@MessageMapping("/send")
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/user")
    public void send(String messaging) {
        MessageModel messageModel = JSONUtil.toBean(messaging, MessageModel.class);
        if (messageModel != null) {
            simpMessagingTemplate.convertAndSend("/topic/logs",messaging);
            simpMessagingTemplate.convertAndSend("/user/talk/" + messageModel.getTo(), messageModel.getFrom() + " 发送了消息：" + messageModel.getMessage());
        }
    }
}
