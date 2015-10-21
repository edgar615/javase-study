package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysDict;
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
 * 系统字典的校验类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysDictValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type(SysDict.class)
                .property("dictCode", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .constraint(new NotNullDef())
                .constraint(new PatternDef().regexp("[0-9a-zA-Z_]+").message(
                        "{msg.error.validation.dictCode.pattern}"))
                .property("dictName", ElementType.FIELD)
                .constraint(new SizeDef().max(32)).constraint(new NotNullDef());
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }

}
