package com.edgar.core.repository;

import com.edgar.core.util.ServiceLookup;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 生成主键的工具类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public abstract class IDUtils {

    private IDUtils() {
        super();
    }

    /**
     * 根据mysql的auto_increment生成主键
     *
     * @return 主键
     */
    public static int getNextId() {
        DataSource dataSource = ServiceLookup.getBean(DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new StatementCallback<Integer>() {

            @Override
            public Integer doInStatement(Statement stmt) throws SQLException,
                    DataAccessException {
                stmt.execute("replace into sequence(stub) values('a')");
                ResultSet resultSet = stmt
                        .executeQuery("select last_insert_id() as id");
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                throw new SQLException("generate id failed");
            }
        });
    }

}
