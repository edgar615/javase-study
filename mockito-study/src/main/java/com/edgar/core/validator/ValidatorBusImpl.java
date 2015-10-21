package com.edgar.core.validator;

import com.edgar.core.util.ServiceLookup;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 14-9-11.
 */
@Service
public class ValidatorBusImpl implements ValidatorBus {
    @Override
    public void validator(Object target, Class<? extends ValidatorStrategy> strategyClass) {
        ValidatorStrategy strategy = ServiceLookup.getBean(strategyClass);
        strategy.validator(target);
    }
}
