package com.edgar.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomerRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String ds = null;
        switch (CustomerContextHolder.getCustomerType()) {
            case BRONZE:
                ds = "MINOR";
                break;
            case SILVER:
                ds = "MAJOR";
                break;
            case GOLD:
                ds = "MAJOR";
                break;

            default:
                break;
        }
        return ds;
    }
}