package com.edgar.core.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ToQueryExample {

    enum QueryType {
        /**
         * =
         */
        EQUALS_TO,
        /**
         * like '%...%'
         */
        CONTAIN,
        /**
         * like '%..'
         */
        BEGIN_WITH
    }

    QueryType value() default QueryType.EQUALS_TO;

    int maxNumOfRecords() default 0;
}
