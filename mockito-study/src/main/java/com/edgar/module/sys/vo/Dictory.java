package com.edgar.module.sys.vo;

import com.edgar.module.sys.repository.domain.SysDict;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将系统字典封装成一个map对象。
 *
 * @author 张雨舟
 * @version 2013-5-10
 */
public class Dictory implements Map<String, SysDict> {

    private SysDict sysDict;

    private final Map<String, SysDict> delegateMap = new HashMap<String, SysDict>();

    public SysDict getSysDict() {
        return sysDict;
    }

    public void setSysDict(SysDict sysDict) {
        this.sysDict = sysDict;
    }

    @Override
    public int size() {
        return delegateMap.size();
    }

    @Override
    public boolean isEmpty() {
        return delegateMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegateMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegateMap.containsValue(value);
    }

    @Override
    public SysDict get(Object key) {
        return delegateMap.get(key);
    }

    @Override
    public SysDict put(String key, SysDict value) {
        return delegateMap.put(key, value);
    }

    @Override
    public SysDict remove(Object key) {
        return delegateMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends SysDict> m) {
        delegateMap.putAll(m);
    }

    @Override
    public void clear() {
        delegateMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return delegateMap.keySet();
    }

    @Override
    public Collection<SysDict> values() {
        return delegateMap.values();
    }

    @Override
    public Set<Entry<String, SysDict>> entrySet() {
        return delegateMap.entrySet();
    }

}