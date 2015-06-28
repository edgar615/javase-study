package com.edgar.designpattern.ocp;

public class Square extends Shape {

    private double itsSide;

    private Point itsTopLeft;

    public double getItsSide() {
        return itsSide;
    }

    public void setItsSide(double itsSide) {
        this.itsSide = itsSide;
    }

    public Point getItsTopLeft() {
        return itsTopLeft;
    }

    public void setItsTopLeft(Point itsTopLeft) {
        this.itsTopLeft = itsTopLeft;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}
