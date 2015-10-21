package com.edgar.kafka.alarm;

/**
 * Created by edgar on 14-10-22.
 */
public class Alarm {
    private int orgCode;

    private String alarmCode;

    private String machineCode;

    public Alarm() {
    }

    public Alarm(int orgCode, String alarmCode, String machineCode) {
        this.orgCode = orgCode;
        this.alarmCode = alarmCode;
        this.machineCode = machineCode;
    }

    public int getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(int orgCode) {
        this.orgCode = orgCode;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
}
