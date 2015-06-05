package com.edgar.mapper;

import com.edgar.core.repository.BaseMapper;
import com.edgar.domain.CompanyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public interface CompanyConfigMapper extends BaseMapper<CompanyConfig, Integer>
{

	final static Logger logger = LoggerFactory.getLogger(CompanyConfigMapper.class);

	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/
    int count();

    void createTable();

    void dropTable();
	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}

