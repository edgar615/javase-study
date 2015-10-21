package com.edgar.module.sys.validator;

import com.edgar.core.validator.AbstractValidatorTemplate;
import com.edgar.module.sys.repository.domain.SysMenu;
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
 * 修改菜单时的校验类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysMenuUpdateValidator extends AbstractValidatorTemplate {

    @Override
    public Validator createValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(
                HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .type(SysMenu.class)
                .property("menuId", ElementType.FIELD)
                .constraint(new NotNullDef())
                .property("menuName", ElementType.FIELD)
                .constraint(new SizeDef().max(32))
                .property("menuPath", ElementType.FIELD)
                .constraint(new SizeDef().max(128))
                .constraint(new PatternDef()
                        .regexp("(/:?[0-9a-zA-Z_]+)*")
                        .message("{msg.error.validation.routeUrl.pattern}"))
                .property("icon", ElementType.FIELD)
                .constraint(new SizeDef().max(32));
        return configuration.addMapping(constraintMapping).buildValidatorFactory()
                .getValidator();
    }
}
