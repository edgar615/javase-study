package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysJob;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.NullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.ElementType;

@Service
public class SysJobUpdateValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping.type(SysJob.class).property("jobId", ElementType.FIELD)
                .constraint(new NotNullDef())
                .property("clazzName", ElementType.FIELD).constraint(new NullDef())
                .property("jobName", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .property("cron", ElementType.FIELD)
                .constraint(new SizeDef().max(32));
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }

}
