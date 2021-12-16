package com.study.boot.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.study.boot.base.CommonResponseEnum.*;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/24 16:05
 */
@Slf4j
@RestControllerAdvice
public class UnifiedExceptionHandler {
    private static final String ENV_PROD = "prod";
    private final MessageSource messageSource;
    private final Boolean isProd;

    public UnifiedExceptionHandler(@Value("${spring.profiles.active:dev}") final String activeProfile, final MessageSource messageSource) {
        this.messageSource = messageSource;
        this.isProd = new HashSet<>(Arrays.asList(activeProfile.split(","))).contains(ENV_PROD);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestPartException.class
    })
    public ErrorResponse badRequestException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(BAD_REQUEST.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    public ErrorResponse noHandlerFoundException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(NOT_FOUND.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public ErrorResponse httpRequestMethodNotSupportedException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(METHOD_NOT_ALLOWED.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            HttpMediaTypeNotAcceptableException.class
    })
    public ErrorResponse httpMediaTypeNotAcceptableException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(NOT_ACCEPTABLE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class
    })
    public ErrorResponse httpMediaTypeNotSupportedException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            AsyncRequestTimeoutException.class
    })
    public ErrorResponse asyncRequestTimeoutException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(SERVICE_UNAVAILABLE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            MissingPathVariableException.class,
            HttpMessageNotWritableException.class,
            ConversionNotSupportedException.class,
    })
    public ErrorResponse handleServletException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            BindException.class
    })
    @ResponseBody
    public ErrorResponse handleBindException(BindException e, WebRequest request) {
        log.error("参数绑定异常", e);
        return wrapperBindingResult(e.getBindingResult(), request);
    }

    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    public ErrorResponse handleValidException(MethodArgumentNotValidException e, WebRequest request) {
        log.error("参数校验异常", e);
        return wrapperBindingResult(e.getBindingResult(), request);
    }

    /**
     * 包装绑定异常结果
     */
    private ErrorResponse wrapperBindingResult(BindingResult bindingResult, WebRequest request) {
        final List<String> errorDesc = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            final StringBuilder msg = new StringBuilder();
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
            errorDesc.add(msg.toString());
        }
        final String desc = isProd ? getLocaleMessage(BAD_REQUEST.getCode(), "") : String.join(", ", errorDesc);
        return new ErrorResponse(BAD_REQUEST.getCode(), desc, request);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = CodeBaseException.class)
    @ResponseBody
    public ErrorResponse handleBusinessException(CodeBaseException e, WebRequest request) {
        log.error("业务异常：" + e.getMessage(), e);
        final ResponseEnum anEnum = e.getAnEnum();
        return new ErrorResponse(anEnum.getCode(), anEnum.getLocaleMessage(e.getArgs()), request);
    }

    /**
     * 未定义异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResponse handleExceptionInternal(Exception e, WebRequest request) {
        log.error("未捕捉异常：" + e.getMessage(), e);
        final Integer code = INTERNAL_SERVER_ERROR.getCode();
        return new ErrorResponse(code, getLocaleMessage(code, e.getMessage()), request);
    }

    private String getLocaleMessage(Integer code, String defaultMsg) {
        try {
            return messageSource.getMessage("" + code, null, defaultMsg, LocaleContextHolder.getLocale());
        }
        catch (Throwable t) {
            log.warn("本地化异常消息发生异常: {}", code);
            return defaultMsg;
        }
    }
}
