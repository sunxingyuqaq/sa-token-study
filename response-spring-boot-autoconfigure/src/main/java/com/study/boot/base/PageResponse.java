package com.study.boot.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/24 16:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends Response<PageData<T>>{
}
