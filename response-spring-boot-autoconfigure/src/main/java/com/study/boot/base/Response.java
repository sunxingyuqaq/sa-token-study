package com.study.boot.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * describe:返回响应体
 *
 * @author admin
 * @date 2021/11/24 16:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Response<T> extends BaseResponse {
    /**
     * 返回内容
     */
    private T data;

}
