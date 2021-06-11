package com.janwes.exception;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.exception
 * @date 2021/6/4 14:22
 * @description 业务异常处理类
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException() {
    }

    public BusinessException(String message) {
        this.code = StatusCode.CUSTOM_ERROR.code();
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
