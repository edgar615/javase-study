package com.edgar.core.repository;

public enum SqlOperator {

    /**
     * is null
     */
    IS_NULL,
    /**
     * is not null
     */
    IS_NOT_NULL,
    /**
     * =
     */
    EQ,
    /**
     * <>
     */
    NE,
    /**
     * >
     */
    GT,
    /**
     * >=
     */
    GOE,
    /**
     * 小于
     */
    LT,
    /**
     * <=
     */
    LOE,
    /**
     * like
     */
    LIKE,
    /**
     * not like
     */
    NOT_LIKE,
    /**
     * in
     */
    IN,
    /**
     * not in
     */
    NOT_IN,
    /**
     * between
     */
    BETWEEN,
    /**
     * not between
     */
    NOT_BETWEEN
}
