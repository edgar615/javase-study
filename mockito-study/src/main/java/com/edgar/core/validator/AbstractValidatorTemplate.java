package com.edgar.core.validator;

import com.edgar.core.util.ExceptionFactory;
import org.apache.commons.lang.Validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验类模板类，所有校验都需要继承此类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public abstract class AbstractValidatorTemplate implements ValidatorStrategy {

    private final Validator validator;

    protected AbstractValidatorTemplate() {
        validator = createValidator();
    }

    /**
     * 创建一个JSR303的validator
     *
     * @return Validator
     */
    protected abstract Validator createValidator();

    @Override
    public final void validator(Object target) {
        Validate.notNull(target);
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(target);
        if (!constraintViolations.isEmpty()) {
            throw ExceptionFactory.inValid(constraintViolations);
        }
    }

}
