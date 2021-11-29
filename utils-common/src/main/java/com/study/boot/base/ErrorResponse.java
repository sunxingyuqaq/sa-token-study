package com.study.boot.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/24 15:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends BaseResponse {

    public ErrorResponse(final Integer code, final String desc) {
        super(code, desc);
    }

    public ErrorResponse(final Integer code, final String desc, final WebRequest request) {
        super(code, desc, extractRequestURI(request));
    }

    public ErrorResponse(final HttpStatus status, final Exception e) {
        super(status.value(), status.getReasonPhrase() + ": " + e.getMessage());
    }

    public ErrorResponse(final HttpStatus status, final Exception e, final WebRequest request) {
        super(status.value(), status.getReasonPhrase() + ": " + e.getMessage(), extractRequestURI(request));
    }

    private static String extractRequestURI(WebRequest request) {
        final String requestUri;
        if (request instanceof ServletWebRequest) {
            ServletRequestAttributes attributes = (ServletWebRequest) request;
            requestUri = attributes.getRequest().getRequestURI();
        } else {
            requestUri = request.getDescription(false);
        }
        return requestUri;
    }
}
