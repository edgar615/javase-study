package com.edgar.designpattern.lsp;

public class Square extends Rectangle {

    @Override
    public void setItsHeight(double itsHeight) {
        super.setItsHeight(itsHeight);
        super.setItsWidth(itsHeight);
    }

    @Override
    public void setItsWidth(double itsWidth) {
        super.setItsWidth(itsWidth);
        super.setItsHeight(itsWidth);
    }
}
