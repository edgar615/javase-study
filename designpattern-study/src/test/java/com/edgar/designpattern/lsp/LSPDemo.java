package com.edgar.designpattern.lsp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LSPDemo {

    public void g(Rectangle r) {
        r.setItsWidth(5);
        r.setItsHeight(4);        
    }
    
    @Test
    public void testLsp() {
        LSPDemo demo = new LSPDemo();
        Rectangle r = new Rectangle();
        demo.g(r);
        assertEquals(20d,r.area(),0);
        
        r = new Square();
        demo.g(r);
        assertEquals(20d,r.area(),0);
    }
} 
