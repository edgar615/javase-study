package com.edgar.designpattern.dip;

public class Button {

    private Lamp itsLamp;
    
    public void poll() {
        itsLamp.turnOn();
    }
}
