package com.study.boot.base;

import lombok.Data;

import java.util.Date;

/**
 * describe:返回响应体
 *
 * @author admin
 * @date 2021/11/24 15:10
 */
@Data
public class BaseResponse {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回内容描述
     */
    private String desc;
    /**
     * 响应时间
     */
    private Date timestamp = new Date();
    /**
     * 请求路径
     */
    private String path;

    public BaseResponse() {
    }

    public BaseResponse(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public BaseResponse(final Integer code, final String desc, final String path) {
        this.code = code;
        this.desc = desc;
        this.path = path;
    }
}
