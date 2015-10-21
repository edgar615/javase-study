package com.edgar.module.sys.init;

import com.edgar.core.init.AppInitializer;
import com.edgar.core.init.Initialization;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysDict;
import com.edgar.module.sys.service.SysDictService;
import com.edgar.module.sys.vo.Dictory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典的工具类.
 *
 * @author 张雨舟
 * @version 2013-5-10
 */
@Service
public class DictoryLoader implements Initialization {

    /**
     * 字典的Map对象。
     */
    private static final Map<String, Dictory> DICT_MAP = new ConcurrentHashMap<String, Dictory>();

    @Autowired
    private SysDictService sysDictService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    public static Map<String, Dictory> getDictMap() {
        return DICT_MAP;
    }

    @Override
    public void init() {
        LOGGER.info("/*****load dictory start*****/");
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("parentCode", "-1");
        List<SysDict> sysDicts = sysDictService.query(example);
        for (SysDict sysDict : sysDicts) {
            Dictory dictory = new Dictory();
            dictory.setSysDict(sysDict);

            example = QueryExample.newInstance();
            example.equalsTo("parentCode", sysDict.getDictCode());
            List<SysDict> sysDictItems = sysDictService.query(example);
            for (SysDict sysDictItem : sysDictItems) {
                dictory.put(sysDictItem.getDictCode(), sysDictItem);
            }
            DICT_MAP.put(sysDict.getDictCode(), dictory);

        }
        LOGGER.info("/*****load dictory finished*****/");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}