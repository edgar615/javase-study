package com.edgar.serviceloader;

import java.io.File;

public class Jeeves
        implements IPersonalServant {
    public void process(File f) {
        System.out.println("Very good, sir.");
    }

    public boolean can(String cmd) {
        if (cmd.equals("fetch tea"))
            return true;
        else
            return false;
    }
}