package com.edgar.core.repository;

import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.Ops;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-5
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class QueryExampleHelper {

    public static QueryExample createUpdateExample(RelationalPathBase<?> pathBase, Object domain, Set<String> pks) {
        QueryExample example = QueryExample.newInstance();
        SqlParameterSource source = new BeanPropertySqlParameterSource(domain);
        List<Path<?>> columns = pathBase.getColumns();
        for (Path<?> path : columns) {
            String name = path.getMetadata().getName();
            String humpName = humpName(name);
            if (pks.contains(name)) {
                Validate.notNull(source.getValue(humpName), "the value of "
                        + name + "cannot be null");
                example.equalsTo(humpName, source.getValue(humpName));
            }
        }
        return example;
    }

    public static QueryExample createUpdateExample(RelationalPathBase<?> pathBase, Object domain) {
        Set<String> pks = new HashSet<String>();
        for (Path<?> path : pathBase.getPrimaryKey().getLocalColumns()) {
            pks.add(path.getMetadata().getName());
        }
        return createUpdateExample(pathBase, domain, pks);
    }

    /**
     * 根据主键创建查询条件
     *
     * @param pathBase Querydsl query type
     * @param pk       主键
     * @return 查询条件
     */
    public static QueryExample createExampleByPk(RelationalPathBase<?> pathBase, Object pk) {
        int numOfPk = pathBase.getPrimaryKey().getLocalColumns().size();
        Validate.isTrue(numOfPk > 0, "primaryKey not exists");
        QueryExample example = QueryExample.newInstance();
        if (numOfPk == 1) {
            example.equalsTo(pathBase.getPrimaryKey().getLocalColumns()
                    .get(0).getMetadata().getName(), pk);
        } else {
            SqlParameterSource source = new BeanPropertySqlParameterSource(pk);
            for (Path<?> path : pathBase.getPrimaryKey().getLocalColumns()) {
                String name = path.getMetadata().getName();
                String humpName = humpName(name);
                example.equalsTo(humpName, source.getValue(name));
            }
        }
        return example;
    }

    public static final List<BooleanExpression> getExpressions(RelationalPathBase<?> pathBase, QueryExample example) {
        List<BooleanExpression> expressions = new ArrayList<BooleanExpression>();
        if (example.isValid()) {
            List<Path<?>> columns = pathBase.getColumns();
            Set<Criteria> criterias = example.getCriterias();
            for (Criteria criteria : criterias) {
                for (Path<?> path : columns) {
                    if (checkColumn(path, criteria.getField())) {
                        expressions.add(caseSqlOp(criteria, path));
                    }
                }
            }
        }
        return expressions;
    }

    public static final List<OrderSpecifier<?>> getOrderSpecs(RelationalPathBase<?> pathBase, QueryExample example) {
        List<OrderSpecifier<?>> specifiers = new ArrayList<OrderSpecifier<?>>();
        List<Path<?>> columns = pathBase.getColumns();
        List<OrderBy> orderBies = example.getOrderBies();
        for (OrderBy orderBy : orderBies) {
            for (Path<?> path : columns) {
                if (checkColumn(path, orderBy.getField())) {
                    if (path instanceof ComparableExpressionBase
                            && Comparable.class
                            .isAssignableFrom(path.getType())) {
                        specifiers.add(caseOrderBy(orderBy, (ComparableExpressionBase<? extends Comparable>) path));
                    }
                }
            }
        }
        return specifiers;
    }

    /**
     * 根据查询条件设置返回值
     *
     * @return 返回的值
     */
    public static final List<Path<?>> getReturnPath(RelationalPathBase<?> pathBase, QueryExample example) {
        if (example.isAll()) {
            return Arrays.asList(pathBase.all());
        } else {
            List<Path<?>> returnPaths = new ArrayList<Path<?>>();
            List<Path<?>> columns = pathBase.getColumns();
            List<String> fields = example.getFields();
            for (String field : fields) {
                for (Path<?> path : columns) {
                    if (checkColumn(path, field)) {
                        returnPaths.add(path);
                    }
                }
            }
            if (returnPaths.isEmpty()) {
                return Arrays.asList(pathBase.all());
            }
            return returnPaths;
        }
    }

    /**
     * 字符串转换，将alarm_user_code转换为alarmUserCode
     *
     * @param source 需要转换的字符串
     * @return 转换后的字符串
     */
    private static String humpName(final String source) {
        Validate.notBlank(source);
        if (StringUtils.contains(source, "_")) {
            String lowerSource = source.toLowerCase();
            String[] words = lowerSource.split("_");
            StringBuilder result = new StringBuilder();
            result.append(words[0]);
            int length = words.length;
            for (int i = 1; i < length; i++) {
                result.append(StringUtils.capitalize(words[i]));
            }
            return result.toString();
        }
        return source;
    }

    /**
     * 检查字段是否是数据库的字段
     *
     * @param path  path
     * @param field 字段名
     * @return 是，返回true,不是，返回false
     */
    private static boolean checkColumn(Path<?> path, String field) {
        return path.getMetadata().getName().equals(field) || path.getMetadata().getName().equals(humpName(field));
    }

    /**
     * 生成不同的查询条件
     *
     * @param criteria 查询标准
     * @param path     查询字段
     * @return QueryDSL的Expression
     */
    @SuppressWarnings("unchecked")
    private static BooleanExpression caseSqlOp(Criteria criteria, Path<?> path) {
        BooleanExpression expression = null;
        switch (criteria.getOp()) {
            case EQ:
                expression = Expressions.predicate(Ops.EQ, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case NE:
                expression = Expressions.predicate(Ops.NE, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case GT:
                expression = Expressions.predicate(Ops.GT, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case GOE:
                expression = Expressions.predicate(Ops.GOE, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case LT:
                expression = Expressions.predicate(Ops.LT, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case LOE:
                expression = Expressions.predicate(Ops.LOE, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case LIKE:
                expression = Expressions.predicate(Ops.LIKE, path,
                        Expressions.constant(criteria.getValue().toString()));
                break;
            case NOT_LIKE:
                expression = Expressions.predicate(Ops.LIKE, path,
                        Expressions.constant(criteria.getValue().toString())).not();
                break;
            case IS_NULL:
                expression = Expressions.predicate(Ops.IS_NULL, path);
                break;
            case IS_NOT_NULL:
                expression = Expressions.predicate(Ops.IS_NOT_NULL, path);
                break;
            case IN:
                if (criteria.getValue() instanceof List<?>) {
                    expression = Expressions.predicate(
                            Ops.IN,
                            path,
                            new ConstantImpl<Collection<? extends String>>(
                                    (Collection<? extends String>) criteria
                                            .getValue()));
                }
                break;
            case NOT_IN:
                if (criteria.getValue() instanceof List<?>) {
                    expression = Expressions.predicate(
                            Ops.IN,
                            path,
                            new ConstantImpl<Collection<? extends String>>(
                                    (Collection<? extends String>) criteria
                                            .getValue())).not();
                }
                break;
            case BETWEEN:
                expression = Expressions.predicate(Ops.BETWEEN, path,
                        Expressions.constant(criteria.getValue().toString()),
                        Expressions.constant(criteria.getSecondValue().toString()));
                break;
            case NOT_BETWEEN:
                expression = Expressions.predicate(Ops.BETWEEN, path,
                        Expressions.constant(criteria.getValue().toString()),
                        Expressions.constant(criteria.getSecondValue().toString()))
                        .not();
                break;
            default:
                break;
        }
        return expression;
    }


    /**
     * 设置排序
     *
     * @param orderBy        排序条件
     * @param expressionBase 排序字段
     * @return OrderSpecifier
     */
    @SuppressWarnings("rawtypes")
    private static OrderSpecifier<?> caseOrderBy(OrderBy orderBy,
                                                 ComparableExpressionBase<? extends Comparable> expressionBase) {
        OrderSpecifier<?> specifier = null;
        switch (orderBy.getOrder()) {
            case ASC:
                specifier = expressionBase.asc();
                break;
            case DESC:
                specifier = expressionBase.desc();
                break;
            default:
                break;
        }
        return specifier;

    }


}
