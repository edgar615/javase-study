package com.edgar.core.exception;

/**
 * 异常码.
 *
 * @author Edgar
 */
public enum BusinessCode implements ErrorCode {

    /**
     * 空指针异常.
     */
    NULL(0),
    /**
     * SQL异常.
     */
    SQL(10),

    /**
     * 数据过期
     */
    EXPIRED(20),

    /**
     * 未登录用户
     */
    UNLOGIN(30),

    /**
     * 密码错误次数过多
     */
    EXCESSIVE_ATTEMPTS(31),

    /**
     * 用户名/密码错误
     */
    USERNAME_PASSWORD_ERROR(32),
    /**
     * 参数非法.
     */
    INVALID(100),
    /**
     * 重复值
     */
    DUPLICATE(101),

    /**
     * 禁止的请求
     */
    FORBIDDEN(102),

    /**
     * 作业错误
     */
    JOB(103),
    /**
     * 参数非法.
     */
    INVALID_PARAMETER(104),
    /**
     * 超时.
     */
    TIMEOUT(105),
    /**
     * 请求非法
     */
    BAD_REQUEST(400),
    /**
     * 权限不足
     */
    UNAUTHORIZED(401),
    /**
     * 资源不存在
     */
    NOT_FOUNT(404),
    /**
     * 请求非法
     */
    UNSUPPORT_METHOD(405),
    /**
     * 系统异常
     */
    APP_ERROR(500);

    /**
     * 异常编码
     */
    private final int number;

    /**
     * @param number 异常码
     */
    private BusinessCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
