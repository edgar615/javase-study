package com.edgar.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统异常。
 *
 * @author 张雨舟
 * @version 1.0
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 4841598191149990231L;

    /**
     * 异常编码
     */
    private ErrorCode errorCode;

    /**
     * 异常的属性值
     */
    private final Map<String, Object> propertyMap = new HashMap<String, Object>();

    public SystemException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public SystemException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(String message, Throwable exception, ErrorCode errorCode) {
        super(message, exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }

    /**
     * 增加异常属性.
     *
     * @param key   键
     * @param value 值
     * @return this
     */
    public SystemException setProperty(String key, Object value) {
        this.propertyMap.put(key, value);
        return this;
    }

    /**
     * 包装异常.等同于 .
     *
     * @param exception 需要包装的异常
     * @return this
     */
    public static SystemException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    /**
     * 包装异常.
     *
     * @param exception 需要包装的异常
     * @param errorCode 异常代码
     * @return this
     */
    public static SystemException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemException) {
            SystemException se = (SystemException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new SystemException(exception.getMessage(), exception,
                        errorCode);
            }
            return se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

}
