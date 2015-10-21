package com.edgar.core.repository;


import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * 查询条件的类.
 *
 * @author Edgar
 * @version 1.0
 */
public class Criteria implements Comparable<Criteria> {

    /**
     * 查询字段，可以是实体类的属性
     */
    private String field;

    /**
     * 查询运算符
     */
    private SqlOperator op;

    /**
     * 查询参数
     */
    private Object value;

    /**
     * 查询参数，用于between的第二个参数
     */
    private Object secondValue;

    public Criteria(String field, SqlOperator op, Object value) {
        super();
        this.field = field;
        this.op = op;
        this.value = value;
    }

    public Criteria(String field, SqlOperator op) {
        super();
        this.field = field;
        this.op = op;
    }

    public Criteria(String field, SqlOperator op, Object value, Object secondValue) {
        this(field, op, value);
        this.secondValue = secondValue;
    }

    @Override
    public int compareTo(Criteria o) {
        return new CompareToBuilder().append(field, o.getField()).append(op, o.getOp())
                .append(value, o.getValue())
                .append(secondValue, o.getSecondValue()).toComparison();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Criteria criteria = (Criteria) o;

        if (!field.equals(criteria.field)) return false;
        if (op != criteria.op) return false;
        if (secondValue != null ? !secondValue.equals(criteria.secondValue) : criteria.secondValue != null)
            return false;
        return !(value != null ? !value.equals(criteria.value) : criteria.value != null);

    }

    @Override
    public int hashCode() {
        int result = field.hashCode();
        result = 31 * result + op.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (secondValue != null ? secondValue.hashCode() : 0);
        return result;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SqlOperator getOp() {
        return op;
    }

    public void setOp(SqlOperator op) {
        this.op = op;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }
}
