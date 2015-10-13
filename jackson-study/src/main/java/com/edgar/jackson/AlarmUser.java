package com.edgar.jackson;

import java.util.HashMap;
import java.util.Map;

public class AlarmUser {
    private String alarmUserId;
    private String alarmUserName;

    public String getAlarmUserId() {
        return alarmUserId;
    }

    public void setAlarmUserId(String alarmUserId) {
        this.alarmUserId = alarmUserId;
    }

    public String getAlarmUserName() {
        return alarmUserName;
    }

    public void setAlarmUserName(String alarmUserName) {
        this.alarmUserName = alarmUserName;
    }

    private Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return map.get(key);
    }
}