package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysRole;
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
 * 修改角色时的校验类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysRoleUpdateValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping.type(SysRole.class).property("roleId", ElementType.FIELD)
                .constraint(new NotNullDef())
                .property("roleName", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .constraint(new PatternDef().regexp("^(?!root$).+$").message("may not be root"))
                .property("roleCode", ElementType.FIELD)

                .constraint(new SizeDef().max(32));
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }

}
