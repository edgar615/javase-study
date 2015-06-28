package com.edgar.designpattern.lsp;


public class Rectangle {

    private double itsWidth;
    private double itsHeight;

    public double area() {
        return itsHeight * itsWidth;
    }
    
    public double getItsWidth() {
        return itsWidth;
    }

    public void setItsWidth(double itsWidth) {
        this.itsWidth = itsWidth;
    }

    public double getItsHeight() {
        return itsHeight;
    }

    public void setItsHeight(double itsHeight) {
        this.itsHeight = itsHeight;
    }

}
