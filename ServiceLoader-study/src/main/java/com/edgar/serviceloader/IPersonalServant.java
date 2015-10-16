package com.edgar.serviceloader;

public interface IPersonalServant {
    // Process a file of commands to the servant
    public void process(java.io.File f)
            throws java.io.IOException;

    public boolean can(String command);
}