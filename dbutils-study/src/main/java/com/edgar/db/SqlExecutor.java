package com.edgar.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2014/11/13.
 */
public interface SqlExecutor<T> {
    T doInConnection(Connection conn) throws SQLException;
}
