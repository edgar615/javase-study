package com.edgar.core.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 *
 * @param <T> 实体类
 * @author Edgar Zhang
 * @version 1.0
 */
public final class Pagination<T> {
    private static final long PAGE_SCOPE = 2;

    private List<T> records;

    private long page;

    private long pageSize;

    private long totalRecords;

    private long totalPages;

    private Pagination(long page, long pageSize, long totalRecords) {
        super();
//        Validate.isTrue(page > 0, "page must > 1");
//        Validate.isTrue(pageSize > 0, "pageSize must > 0");
        this.page = page;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        long pages = totalRecords / pageSize;
        if (totalRecords > pageSize * pages) {
            pages++;
        }
        this.totalPages = pages;
    }

    /**
     * 创建一个Pagination类
     *
     * @param page         页码
     * @param pageSize     每页显示的记录数
     * @param totalRecords 总记录数
     * @param records      当前页显示的集合
     * @param <T>          实体类
     * @return Pagination
     */
    public static <T> Pagination<T> newInstance(long page, long pageSize, long totalRecords,
                                                List<T> records) {
        Pagination<T> pagination = new Pagination<T>(page, pageSize, totalRecords);
        pagination.setRecords(records);
        return pagination;
    }

    /**
     * 返回页码列表
     * <p/>
     * <pre>
     * page 1: 1,2,3,...,41,42
     * page 2: 1,2,3,4,...,41,42
     * page 3: 1,2,3,4,5,...,41,42
     * page 4: 1,2,3,4,5,6,...,41,42
     * page 5: 1,2,3,4,5,6,7,...,41,42
     * page 6: 1,2,3,4,5,6,7,8,...,41,42
     * page 7: 1,2,..,5,6,7,8,,9...,41,42
     * page 8: 1,2,..,6,7,8,,9,10...,41,42
     * page 35: 1,2,..,33,34,35,36,37,...,41,42
     * page 36: 1,2,..,34,35,36,37,38,...,41,42
     * page 37: 1,2,..,35,36,37,38,39,40,41,42
     * page 38: 1,2,..,36,37,38,39,40,41,42
     * page 39: 1,2,..,37,38,39,40,41,42
     * page 40: 1,2,..,38,39,40,41,42
     * page 41: 1,2,..,39,40,41,42
     * page 42: 1,2,..,40,41,42
     * </pre>
     *
     * @return 页码的集合
     */
    public List<Object> getPageList() {
        List<Object> pageList = new ArrayList<Object>();
        if (totalPages < 8) {
            for (long i = 1; i <= totalPages; i++) {
                pageList.add(i);
            }
        } else {
            pageList.add(1L);
            pageList.add(2L);

            setScope(pageList);

            pageList.add(totalPages - 1);
            pageList.add(totalPages);
        }
        return pageList;
    }

    /**
     * 设置页面的中间值
     *
     * @param pageList 页码的集合
     */
    private void setScope(List<Object> pageList) {
        long startPage = page - PAGE_SCOPE;
        long lastPage = page + PAGE_SCOPE;

        if (startPage > 4) {
            pageList.add("...");
        } else {
            pageList.add(3L);
        }
        for (long i = startPage; i <= lastPage; i++) {
            if (i > 3 && i < totalPages - 1) {
                pageList.add(i);
            }
        }
        if (lastPage < totalPages - 2) {
            pageList.add("...");
        }
    }

    /**
     * 下一页页码
     *
     * @return 下一页页码
     */
    public long getNextPage() {
        if (this.page >= this.totalPages) {
            return this.page;
        }
        return this.page + 1;
    }

    /**
     * 上一页页码
     *
     * @return 上一页页码
     */
    public long getPrevPage() {
        if (this.page == 1) {
            return 1;
        }
        return this.page - 1;
    }

    public static long getPageScope() {
        return PAGE_SCOPE;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}