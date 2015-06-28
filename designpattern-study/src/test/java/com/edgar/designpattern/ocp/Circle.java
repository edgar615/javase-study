package com.edgar.designpattern.ocp;

public class Circle extends Shape {


    private double itsRadius;

    private Point itsCenter;


    public double getItsRadius() {
        return itsRadius;
    }

    public void setItsRadius(double itsRadius) {
        this.itsRadius = itsRadius;
    }

    public Point getItsCenter() {
        return itsCenter;
    }

    public void setItsCenter(Point itsCenter) {
        this.itsCenter = itsCenter;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}
