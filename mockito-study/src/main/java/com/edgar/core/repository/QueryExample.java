package com.edgar.core.repository;

import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * 查询条件
 *
 * @author Edgar Zhange
 * @version 1.0
 */
public class QueryExample {
    private static final String LIKE = "%";

    private final Set<Criteria> criterias = new TreeSet<Criteria>();

    private final List<OrderBy> orderBies = new ArrayList<OrderBy>();

    private final List<String> fields = new ArrayList<String>();

    private int limit;

    private int offset;

    private int orderbySort;

    private int maxNumOfRecords;

    private boolean distinct;

    private QueryExample() {
        super();
    }

    /**
     * 创建QueryExample
     *
     * @return QueryExample
     */
    public static QueryExample newInstance() {
        return new QueryExample();
    }

    /**
     * 设置distinct
     * @return
     */
    public QueryExample distinct() {
        this.distinct = true;
        return this;
    }

    /**
     * 设置limit
     *
     * @param limit 现在返回的记录数
     * @return QueryExample
     */
    public QueryExample limit(int limit) {
        // Validate.isTrue(limit > -1, "limit必须大于0");
        this.limit = limit;
        return this;
    }

    /**
     * 设置offset
     *
     * @param offset 偏移量
     * @return QueryExample
     */
    public QueryExample offset(int offset) {
        Validate.isTrue(offset >= 0, "offset must be equals or  greate than 0");
        this.offset = offset;
        return this;
    }

    public int getMaxNumOfRecords() {
        return maxNumOfRecords;
    }

    public void setMaxNumOfRecords(int maxNumOfRecords) {
        this.maxNumOfRecords = maxNumOfRecords;
    }

    /**
     * 返回排序后的排序条件
     *
     * @return 排序的集合
     */
    public List<OrderBy> getOrderBies() {
        Collections.sort(orderBies);
        return Collections.unmodifiableList(orderBies);
    }

    public Set<Criteria> getCriterias() {
        return Collections.unmodifiableSet(criterias);
    }

    public List<String> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public void clearFields() {
        fields.clear();
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    /**
     * 增加返回的字段
     *
     * @param field 字段
     * @return QueryExample
     */
    public QueryExample addField(String field) {
        Validate.notBlank(field, "field cannot be null");
        this.fields.add(field);
        return this;
    }

    /**
     * 增加返回的字段
     *
     * @param fields 字段的集合
     * @return QueryExample
     */
    public QueryExample addFields(List<String> fields) {
        Validate.notEmpty(fields, "field cannot be null");
        this.fields.addAll(fields);
        return this;
    }

    /**
     * 升序
     *
     * @param field 排序字段
     * @return QueryExample
     */
    public QueryExample asc(String field) {
        Validate.notBlank(field, "field cannot be null");
        OrderBy orderBy = OrderBy.asc(field, orderbySort++);
        orderBies.add(orderBy);
        return this;
    }

    /**
     * 降序
     *
     * @param field 排序字段
     * @return QueryExample
     */
    public QueryExample desc(String field) {
        Validate.notBlank(field, "field cannot be null");
        OrderBy orderBy = OrderBy.desc(field, orderbySort++);
        orderBies.add(orderBy);
        return this;
    }

    public boolean isValid() {
        return criterias.size() > 0;
    }

    public boolean isAll() {
        return fields.size() == 0;
    }

    public boolean isDistinct() {
        return distinct;
    }

    /**
     * 重置查询条件
     *
     * @return QueryExample
     */
    public QueryExample clear() {
        this.criterias.clear();
        this.orderBies.clear();
        this.orderbySort = 0;
        this.fields.clear();
        return this;
    }

    /**
     * = 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample equalsTo(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.EQ, value);
    }

    /**
     * <> 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample notEqualsTo(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.NE, value);
    }

    /**
     * > 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample greaterThan(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.GT, value);
    }

    /**
     * >= 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample greaterThanOrEqualTo(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.GOE, value);
    }

    /**
     * < 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample lessThan(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LT, value);
    }

    /**
     * <= 查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample lessThanOrEqualTo(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LOE, value);
    }

    /**
     * like '...'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample like(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LIKE, value);
    }

    /**
     * not like '...'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample notLike(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.NOT_LIKE, value);
    }

    /**
     * like '...%'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample beginWtih(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LIKE, value + LIKE);
    }

    /**
     * not like '...%'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample notBeginWith(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.NOT_LIKE, value + LIKE);
    }

    /**
     * like '%...'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample endWtih(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LIKE, LIKE + value);
    }

    /**
     * not like '%...'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample notEndWith(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.NOT_LIKE, LIKE + value);
    }

    /**
     * like '%...%'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample contain(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.LIKE, LIKE + value + LIKE);
    }

    /**
     * not like '%...%'查询
     *
     * @param field 查询字段
     * @param value 比较值
     * @return QueryExample
     */
    public QueryExample notContain(String field, Object value) {
        Validate.notNull(value);
        return addCriteria(field, SqlOperator.NOT_LIKE, LIKE + value + LIKE);
    }

    /**
     * in 查询
     *
     * @param field  查询字段
     * @param values 比较值
     * @return QueryExample
     */
    public QueryExample in(String field, List<Object> values) {
        Validate.notNull(values);
        Validate.notEmpty(values);
        return addCriteria(field, SqlOperator.IN, values);
    }

    /**
     * not in 查询
     *
     * @param field  查询字段
     * @param values 比较值
     * @return QueryExample
     */
    public QueryExample notIn(String field, List<Object> values) {
        Validate.notNull(values);
        Validate.notEmpty(values);
        return addCriteria(field, SqlOperator.NOT_IN, values);
    }

    /**
     * between查询
     *
     * @param field  查询字段
     * @param value1 比较值
     * @param value2 比较值
     * @return QueryExample
     */
    public QueryExample between(String field, Object value1, Object value2) {
        Validate.notNull(value1);
        Validate.notNull(value2);
        return addCriteria(field, SqlOperator.BETWEEN, value1, value2);
    }

    /**
     * not between查询
     *
     * @param field  查询字段
     * @param value1 比较值
     * @param value2 比较值
     * @return QueryExample
     */
    public QueryExample notBetween(String field, Object value1, Object value2) {
        Validate.notNull(value1);
        Validate.notNull(value2);
        return addCriteria(field, SqlOperator.NOT_BETWEEN, value1, value2);
    }

    /**
     * is null查询
     *
     * @param field 查询字段
     * @return QueryExample
     */
    public QueryExample isNull(String field) {
        Validate.notNull(field);
        return addCriteria(field, SqlOperator.IS_NULL);
    }

    /**
     * is not null查询
     *
     * @param field 查询字段
     * @return QueryExample
     */
    public QueryExample isNotNull(String field) {
        Validate.notNull(field);
        return addCriteria(field, SqlOperator.IS_NOT_NULL);
    }

    /**
     * 增加一个查询条件
     *
     * @param field 查询字段
     * @param op    查询条件
     * @return QueryExample
     */
    private QueryExample addCriteria(String field, SqlOperator op) {
        Validate.notBlank(field, "field cannot be null");
        criterias.add(new Criteria(field, op));
        return this;
    }

    /**
     * 增加一个查询条件
     *
     * @param field 查询字段
     * @param op    查询条件
     * @param value 比较值
     * @return QueryExample
     */
    private QueryExample addCriteria(String field, SqlOperator op, Object value) {
        Validate.notBlank(field, "field cannot be null");
        Validate.notNull(value, "value cannot be null");
        criterias.add(new Criteria(field, op, value));
        return this;
    }

    /**
     * 增加一个查询条件
     *
     * @param field  查询字段
     * @param op     查询条件
     * @param value1 比较值
     * @param value2 比较值
     * @return QueryExample
     */
    private QueryExample addCriteria(String field, SqlOperator op, Object value1, Object value2) {
        Validate.notBlank(field, "field cannot be null");
        Validate.notNull(value1, "value1 cannot be null");
        Validate.notNull(value2, "value2 cannot be null");
        criterias.add(new Criteria(field, op, value1, value2));
        return this;
    }

    /**
     * 判断是否包含某个查询条件
     *
     * @param criteria 查询条件
     * @return 如果包含，则返回true
     */
    public boolean containCriteria(Criteria criteria) {
        return getCriterias().contains(criteria);
    }

}
