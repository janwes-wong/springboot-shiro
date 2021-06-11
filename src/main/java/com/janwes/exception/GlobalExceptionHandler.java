package com.janwes.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.exception
 * @date 2021/6/4 13:39
 * @description 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 当系统出现Exception(最大异常)异常时执行
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        log.error(">>> 服务器系统异常......", e);
        return Result.error();
    }

    @ExceptionHandler(BusinessException.class)
    public Result handlerBusinessException(BusinessException e) {
        log.error(">>> 业务异常......", e);
        return Result.errorMessage(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result handlerUnauthorizedException(UnauthorizedException e) {
        log.error(">>> 权限不足异常......", e);
        return Result.errorMessage(401, e.getMessage(), "权限不足请与管理员联系");
    }
}
