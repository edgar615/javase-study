package com.edgar.core.command;

public class ChainGetSysUser implements Command, ChainCommand {

    private int userId;

    @Override
    public Command nextCommand() {
        return new GetSysUser(userId);
    }

    public ChainGetSysUser(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
