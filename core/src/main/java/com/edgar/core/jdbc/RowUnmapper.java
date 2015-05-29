package com.edgar.core.jdbc;

import java.util.Map;

public interface RowUnmapper<T> {
    Map<String, Object> mapColumns(T t);
}