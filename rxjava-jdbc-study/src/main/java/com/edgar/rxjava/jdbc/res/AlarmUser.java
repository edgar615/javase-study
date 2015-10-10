package com.edgar.rxjava.jdbc.res;

/**
 * Created by Administrator on 2015/10/10.
 */
public class AlarmUser {
    private String alarmUserId;
    private String alarmUserName;

    public AlarmUser(String alarmUserId, String alarmUserName) {
        this.alarmUserId = alarmUserId;
        this.alarmUserName = alarmUserName;
    }

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
}
