package com.edgar.core.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class DefaultBusinessService implements BusinessService {

    /**
     * {@inheritDoc}
     */
    public String convertToUpperCase(String input) {

        if ("returnnull".equalsIgnoreCase(input)) {
            return null;
        }

        return input.toUpperCase();
    }

}