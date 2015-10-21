package com.edgar.core.util;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

/**
 * 创建异常的工厂类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public abstract class ExceptionFactory {

    /**
     * 用户未登录
     *
     * @return SystemException
     */
    public static SystemException unLogin() {
        return new SystemException(BusinessCode.UNLOGIN, " User UnLogin");
    }

    /**
     * 用户名/密码错误
     *
     * @return SystemException
     */
    public static SystemException userOrPasswordError() {
        return new SystemException(BusinessCode.USERNAME_PASSWORD_ERROR,
                "Username or Password is not correct!");
    }

    /**
     * 没有访问权限
     *
     * @return SystemException
     */
    public static SystemException unAuthorized() {
        return new SystemException(BusinessCode.UNAUTHORIZED,
                "No Authorization Right");
    }

    public static SystemException timeout() {
        return new SystemException(BusinessCode.TIMEOUT,
                "Request timed out");
    }

    /**
     * 请求被禁止
     *
     * @return SystemException
     */
    public static SystemException forbidden() {
        return new SystemException(BusinessCode.FORBIDDEN);
    }

    /**
     * 密码错误次数过多
     *
     * @param num 错误次数
     * @return SystemException
     */
    public static SystemException ExcessiveAttempts(int num) {
        return new SystemException(BusinessCode.EXCESSIVE_ATTEMPTS,
                "Amounts of wrong password, please landed an hour later")
                .setProperty("ExcessiveAttempts", num);
    }

    /**
     * 空
     *
     * @return SystemException
     */
    public static SystemException isNull() {
        return new SystemException(BusinessCode.NULL, "The data is null");
    }

    /**
     * 重复值
     *
     * @param msg 异常消息
     * @return SystemException
     */
    public static SystemException duplicate(String msg) {
        return new SystemException(BusinessCode.DUPLICATE, msg);
    }

    /**
     * 数据过期
     *
     * @return SystemException
     */
    public static SystemException expired() {
        return new SystemException(BusinessCode.EXPIRED,
                "Data has been updated or deleted");
    }

    /**
     * 资源不存在
     *
     * @return SystemException
     */
    public static SystemException notFound() {
        return new SystemException(BusinessCode.NOT_FOUNT,
                "There is no resource");
    }

    /**
     * 请求非法
     *
     * @return SystemException
     */
    public static SystemException badRequest() {
        return new SystemException(BusinessCode.BAD_REQUEST, "Bad request");
    }

    /**
     * 不支持的请求
     *
     * @return SystemException
     */
    public static SystemException unSupportMethod() {
        return new SystemException(BusinessCode.UNSUPPORT_METHOD,
                "Unsupport method");
    }

    /**
     * 系统错误
     *
     * @return SystemException
     */
    public static SystemException appError() {
        return new SystemException(BusinessCode.APP_ERROR, "System error");
    }

    /**
     * 系统错误
     *
     * @param  msg
     * @return SystemException
     */
    public static SystemException appError(String msg) {
        return new SystemException(BusinessCode.APP_ERROR, msg);
    }

    /**
     * 系统错误
     *
     * @return SystemException
     */
    public static SystemException appError(Throwable t) {
        return new SystemException(BusinessCode.APP_ERROR, t.getMessage());
    }

    /**
     * 作业错误
     *
     * @return SystemException
     */
    public static SystemException job() {
        return new SystemException(BusinessCode.JOB, "Job failed");
    }

    /**
     * 根据spring的valid校验结果创建SystemException
     *
     * @param result BindingResult
     * @return SystemException
     */
    public static SystemException invalidBindResult(BindingResult result) {
        SystemException e = new SystemException(BusinessCode.INVALID,
                "Invalid parameter");
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                e.setProperty(fieldError.getField(), fieldError.getField()
                        + fieldError.getDefaultMessage());
            }
        }
        return e;
    }

    /**
     * 根据jsr303的valid校验结果创建SystemException
     *
     * @param constraintViolations 校验结果的结果集
     * @return SystemException
     */
    public static SystemException inValid(
            Set<ConstraintViolation<Object>> constraintViolations) {
        SystemException e = new SystemException(BusinessCode.INVALID,
                "Invalid parameter");
        for (ConstraintViolation<?> violation : constraintViolations) {
            e.setProperty(violation.getPropertyPath().toString(),
                    violation.getMessage());
        }
        return e;
    }

    /**
     * 参数错误
     *
     * @param msg 错误提示
     * @return SystemException
     */
    public static SystemException inValidParameter(String msg) {
        return new SystemException(BusinessCode.INVALID_PARAMETER,
                msg);
    }
}
