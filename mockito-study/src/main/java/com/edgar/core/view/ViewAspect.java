package com.edgar.core.view;

import com.edgar.core.util.ExceptionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;

/**
 * rest返回视图的AOP类.
 *
 * @author 张雨舟
 * @version 2013/05/09
 */
@Aspect
@Controller
public class ViewAspect {

    /**
     * 切面
     */
    @Pointcut("execution(@org.springframework.web.bind.annotation.ResponseBody * com.edgar..*Resource.*(..))")
    public void returnValuePointCut() {
    }

    /**
     * 如果视图返回null，则抛出资源不存在的异常.
     *
     * @param jp  拦截点对象
     * @param val 日志的annotation类
     */
    @AfterReturning(pointcut = "returnValuePointCut()", returning = "val")
    public void afterReturning(JoinPoint jp, Object val) {
        if (val == null) {
            throw ExceptionFactory.notFound();
        }
    }
}
