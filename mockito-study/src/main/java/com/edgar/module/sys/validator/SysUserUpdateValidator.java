package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysUser;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.PatternDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.ElementType;

/**
 * 修改用户时的校验类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysUserUpdateValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type(SysUser.class)
                .property("userId", ElementType.FIELD)
                .constraint(new NotNullDef())
                .property("username", ElementType.FIELD)
                .constraint(new SizeDef().max(16).min(3))
                .constraint(new PatternDef().regexp("[a-zA-Z][0-9a-zA-Z_]*")
                        .message("{msg.error.validation.username.pattern}"))
                .property("password", ElementType.FIELD)
                .constraint(new SizeDef().max(16).min(6))
                .property("fullName", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .property("email", ElementType.FIELD).constraint(new EmailDef())
                .constraint(new SizeDef().max(64));
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }
}
