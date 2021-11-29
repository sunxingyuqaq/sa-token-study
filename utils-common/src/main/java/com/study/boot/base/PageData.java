package com.study.boot.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/24 17:00
 */
@Data
public class PageData<T> extends BaseResponse {

    private int pageSize = 10;
    private int pageNo = 0;
    private int totals = 0;
    private List<T> records = new ArrayList<>();
}
