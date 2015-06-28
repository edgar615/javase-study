package com.edgar.designpattern.monostate;

public class Monostate {

    private static int itsX = 0;

    public Monostate() {
    }

    public int getItsX() {
        return itsX;
    }

    public void setItsX(int itsX) {
        Monostate.itsX = itsX;
    }

}
