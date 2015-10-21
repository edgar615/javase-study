package com.edgar.core.query;

import com.edgar.core.repository.Pagination;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginationTest {

    @Test
    public void testTotalLt8() {
        for (int i = 1; i < 8; i++) {
            List<Object> pageList = new ArrayList<Object>();
            for (long j = 1; j <= i; j++) {
                pageList.add(j);
            }
            for (int j = 1; j <= i; j++) {
                Pagination<Integer> pagination = Pagination.newInstance(j, 1,
                        i, new ArrayList<Integer>());
                Assert.assertArrayEquals(pageList.toArray(), pagination
                        .getPageList().toArray());
            }
        }

    }

    @Test
    public void testPage1Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add("...");
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(1, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage2Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add(4l);
        pageList.add("...");
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(2, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage3Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add(4l);
        pageList.add(5l);
        pageList.add("...");
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(3, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage4Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add(4l);
        pageList.add(5l);
        pageList.add(6l);
        pageList.add("...");
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(4, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage5Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add(4l);
        pageList.add(5l);
        pageList.add(6l);
        pageList.add(7l);
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(5, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage6Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add(3l);
        pageList.add(4l);
        pageList.add(5l);
        pageList.add(6l);
        pageList.add(7l);
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(6, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage7Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add("...");
        pageList.add(5l);
        pageList.add(6l);
        pageList.add(7l);
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(7, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage8Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add("...");
        pageList.add(6l);
        pageList.add(7l);
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(8, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

    @Test
    public void testPage9Total9() {
        List<Object> pageList = new ArrayList<Object>();
        pageList.add(1l);
        pageList.add(2l);
        pageList.add("...");
        pageList.add(7l);
        pageList.add(8l);
        pageList.add(9l);
        Pagination<Integer> pagination = Pagination.newInstance(9, 1, 9,
                new ArrayList<Integer>());
        Assert.assertArrayEquals(pageList.toArray(), pagination.getPageList()
                .toArray());
    }

}
