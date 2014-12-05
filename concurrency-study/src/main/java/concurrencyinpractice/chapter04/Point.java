package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.Immutable;

/**
 * 由于Point类是不可变的，因而它是线程安全的。
 */
@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}