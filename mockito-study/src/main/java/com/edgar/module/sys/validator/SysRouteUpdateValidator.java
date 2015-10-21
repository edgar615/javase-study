package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysRoute;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.PatternDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.ElementType;

/**
 * 修改路由时的校验类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysRouteUpdateValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type(SysRoute.class)
                .property("routeId", ElementType.FIELD)
                .constraint(new NotNullDef())
                .property("name", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .property("url", ElementType.FIELD)
                .constraint(new SizeDef().max(128))
                .constraint(new PatternDef()
                        .regexp("(/:?[0-9a-zA-Z_]+)*")
                        .message("{msg.error.validation.routeUrl.pattern}"));
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }
}
