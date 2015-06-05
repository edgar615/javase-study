package com.edgar.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Administrator on 2015/6/5.
 */
@Intercepts({@Signature(
        type= StatementHandler.class,
        method = "prepare",
        args = {Connection.class})})
public class PaginationInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        Object[] objs = invocation.getArgs();
        System.out.println(invocation.getMethod().getName());
        if (objs != null) {
            for (Object obj : objs) {
                System.out.println(obj);
            }
        }
        System.out.println("boundSql.sql:" + statementHandler.getBoundSql().getSql());
        System.out.println(statementHandler.getBoundSql().getParameterMappings());
        if (statementHandler.getBoundSql().getParameterObject() != null) {
            System.out.println(statementHandler.getBoundSql().getParameterObject().getClass());
        }
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
        System.out.println(originalSql);
        RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
        invocation.proceed();
        if(rowBounds ==null|| rowBounds == RowBounds.DEFAULT){
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
