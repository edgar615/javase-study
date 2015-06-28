package com.edgar.designpattern.ocp;


public class DrawAllShape {

    public void DrawAllShapes(Shape[] shapes, int n) {
       for (Shape s : shapes) {
        s.draw();
       }
    }

}
