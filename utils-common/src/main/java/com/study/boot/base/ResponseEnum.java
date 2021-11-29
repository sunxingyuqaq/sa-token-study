package com.study.boot.base;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/24 15:58
 */
public interface ResponseEnum {

    /**
     * code
     *
     * @return
     */
    Integer getCode();

    /**
     * message
     *
     * @return
     */
    String getMessage();

    /**
     * getLocaleMessage
     *
     * @return
     */
    default String getLocaleMessage() {
        return getLocaleMessage(null);
    }

    /**
     * getLocaleMessage
     * @param args
     * @return
     */
    String getLocaleMessage(Object[] args);
}
