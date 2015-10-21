package com.edgar.core.repository;

import com.mysema.query.sql.Configuration;
import com.mysema.query.sql.MySQLTemplates;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-10
 * Time: 上午9:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConfigurationFactory {
    private static final Configuration CONFIGURATION = new Configuration(new MySQLTemplates());

    public static final Configuration createConfiguration(String configKey) {

        return CONFIGURATION;
    }
}
