package com.edgar.core.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/29.
 */
public abstract class JdbcRepository<T extends Persistable<ID>, ID extends Serializable> {
    private final RowMapper<T> rowMapper;
    private final RowUnmapper<T> rowUnmapper;

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcRepository(RowMapper<T> rowMapper, RowUnmapper<T> rowUnmapper) {
        this.rowMapper = rowMapper;
        this.rowUnmapper = rowUnmapper;
    }

    public Pagination<T> pagination(String sql, int page, int pageSize) {
//        return pagination(example, page, pageSize);
        return null;
    }

    public int insert(String sql, T entity) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, this.rowUnmapper.mapColumns(entity));
    }

    public int updateByPrimaryKey(String sql, T entity) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, this.rowUnmapper.mapColumns(entity));
    }

    protected int deleteByPrimaryKey(String sql, ID id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, new Object[] {id});
    }

    public abstract int deleteByPrimaryKey(ID id);

    public T selectByPrimaryKey(String sql, ID id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(sql, new Object[] {id},  this.rowMapper);
    }

//    public int insert(String sql, ID id) {
//        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        return jdbcTemplate.update();
//    }


}
