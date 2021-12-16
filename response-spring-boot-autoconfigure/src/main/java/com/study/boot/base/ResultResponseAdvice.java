package com.study.boot.base;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/25 14:25
 */
@RestControllerAdvice
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getGenericParameterType().equals(Response.class) &&
                !returnType.getGenericParameterType().equals(PageResponse.class) &&
                !returnType.getGenericParameterType().equals(ErrorResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null || body instanceof Response || body instanceof ErrorResponse || body instanceof PageData) {
            return body;
        }
        final Response<Object> result = new Response<>();
        result.setCode(200);
        result.setDesc("请求成功");
        result.setData(body);
        result.setPath(request.getURI().getPath());
        if (returnType.getGenericParameterType().equals(String.class)) {
            return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
        }
        return result;
    }
}
