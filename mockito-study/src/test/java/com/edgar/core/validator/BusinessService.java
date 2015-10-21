package com.edgar.core.validator;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public interface BusinessService {

    /**
     * Convert the provided input String to the upper-case representation.
     *
     * @param input String to be converted to upper-case
     * @return Converted String. Never returns null.
     */
    @NotNull(message = "Null returns are not permitted")
    String convertToUpperCase(@Size(max = 2)
                              String input);

}