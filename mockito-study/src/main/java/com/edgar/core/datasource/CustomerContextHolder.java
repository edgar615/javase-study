package com.edgar.core.datasource;

import org.apache.commons.lang3.Validate;

public class CustomerContextHolder {
    private static final ThreadLocal<CustomerType> contextHolder = new ThreadLocal<CustomerType>();

    public static void setCustomerType(CustomerType customerType) {
        Validate.notNull(customerType, "customerType cannot be null");
        contextHolder.set(customerType);
    }

    public static CustomerType getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}