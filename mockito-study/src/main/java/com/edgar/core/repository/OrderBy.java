package com.edgar.core.repository;

/**
 * 排序类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public final class OrderBy implements Comparable<OrderBy> {

    private final String field;

    private final Order order;

    private int sorted;

    private OrderBy(String field, Order order) {
        super();
        this.field = field;
        this.order = order;
    }

    /**
     * 升序排序
     *
     * @param field 排序字段
     * @return OrderBy
     */
    public static OrderBy asc(String field) {
        return new OrderBy(field, Order.ASC);
    }

    /**
     * 降序排序
     *
     * @param field 排序字段
     * @return OrderBy
     */
    public static OrderBy desc(String field) {
        return new OrderBy(field, Order.DESC);
    }

    /**
     * 升序排序
     *
     * @param field  排序字段
     * @param sorted 排序位置
     * @return OrderBy
     */
    public static OrderBy asc(String field, int sorted) {
        OrderBy orderBy = new OrderBy(field, Order.ASC);
        orderBy.setSorted(sorted);
        return orderBy;
    }

    /**
     * 降序排序
     *
     * @param field  排序字段
     * @param sorted 排序位置
     * @return OrderBy
     */
    public static OrderBy desc(String field, int sorted) {
        OrderBy orderBy = new OrderBy(field, Order.DESC);
        orderBy.setSorted(sorted);
        return orderBy;
    }

    @Override
    public int compareTo(OrderBy o) {
        if (sorted < o.sorted) {
            return -1;
        } else if (sorted == o.sorted) {
            return 0;
        }
        return 1;
    }

    public String getField() {
        return field;
    }

    public Order getOrder() {
        return order;
    }

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }
}