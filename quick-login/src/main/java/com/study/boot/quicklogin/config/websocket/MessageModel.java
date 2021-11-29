package com.study.boot.quicklogin.config.websocket;

import lombok.Data;

import java.io.Serializable;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/25 17:03
 */
@Data
public class MessageModel implements Serializable {

    private String from;

    private String to;

    private String message;
}
