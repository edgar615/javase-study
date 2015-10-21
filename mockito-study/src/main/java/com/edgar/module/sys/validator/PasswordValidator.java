package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.vo.ChangePasswordVo;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.ElementType;

@Service
public class PasswordValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping.type(ChangePasswordVo.class)
                .property("oldpassword", ElementType.FIELD)
                .constraint(new SizeDef().max(16).min(6))
                .constraint(new NotNullDef())
                .property("newpassword", ElementType.FIELD)
                .constraint(new SizeDef().max(16).min(6))
                .constraint(new NotNullDef())
                .property("retypepassword", ElementType.FIELD)
                .constraint(new SizeDef().max(16).min(6))
                .constraint(new NotNullDef());
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }

}
