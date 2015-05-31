package com.edgar.core.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/29.
 */
public class JdbcRepository<T extends Persistable<ID>, ID extends Serializable> {
    private final RowMapper<T> rowMapper;
    private final RowUnmapper<T> rowUnmapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Sql2o sql2o;

    public void setSql2o(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

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

    public int insert2(String sql, T entity) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(sql);//.bind(entity).executeUpdate().getResult();
            for (Map.Entry<String, Object> entry : this.rowUnmapper.mapColumns(entity).entrySet()) {
                query.addParameter(entry.getKey(), entry.getValue());
            }
            return query.executeUpdate().getResult();
        }
    }

    public int insert(String sql, T entity) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, this.rowUnmapper.mapColumns(entity));
    }

    public int updateByPrimaryKey(String sql, T entity) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, this.rowUnmapper.mapColumns(entity));
    }

    public int deleteByPrimaryKey(String sql, T entity) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, this.rowUnmapper.mapColumns(entity));
    }

//    public int insert(String sql, ID id) {
//        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        return jdbcTemplate.update();
//    }


}
