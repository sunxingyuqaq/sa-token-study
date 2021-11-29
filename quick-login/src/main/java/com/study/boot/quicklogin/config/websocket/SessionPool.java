package com.study.boot.quicklogin.config.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/26 09:22
 */
@Slf4j
public class SessionPool {

    public static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    public static final Map<String, Session> MAPS = new ConcurrentHashMap<>();

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session
     * @param message
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
        }
        catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
        }
    }

    public static void removeSession(Session session) throws IOException {
        for (Map.Entry<String, Session> sessionEntry : MAPS.entrySet()) {
            String key = sessionEntry.getKey();
            Session value = sessionEntry.getValue();
            if (value.getId().equals(session.getId())) {
                if (value.isOpen()) {
                    value.close();
                }
                MAPS.remove(key);
            }
        }
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    public static void broadCastInfo(String message) throws IOException {
        for (Map.Entry<String, Session> entry : SessionPool.MAPS.entrySet()) {
            Session session = entry.getValue();
            boolean open = session.isOpen();
            if (open) {
                sendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     *
     * @param message
     * @param userId
     * @throws IOException
     */
    public static void sendMessage(String message, String userId) throws IOException {
        Session session = SessionPool.MAPS.get(userId);
        if (session != null) {
            sendMessage(session, message);
        }
        else {
            log.warn("没有找到你指定userId的会话：{}", userId);
        }
    }
}
