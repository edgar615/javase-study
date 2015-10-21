package com.edgar.core.query;

import com.edgar.core.repository.Criteria;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.repository.SqlOperator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CriteriaTest {

    @Test
    public void testSort() {
        Criteria criteria = new Criteria("a", SqlOperator.IS_NOT_NULL);
        Criteria criteria2 = new Criteria("b", SqlOperator.EQ, "b");
        Assert.assertEquals(-1, criteria.compareTo(criteria2));
        criteria2 = new Criteria("a", SqlOperator.EQ, "b");
        Assert.assertEquals(-1, criteria.compareTo(criteria2));
        criteria2 = new Criteria("1", SqlOperator.EQ, "b");
        Assert.assertTrue(criteria.compareTo(criteria2) > 0);
    }

    @Test
    public void testEquals() {
        Criteria criteria = new Criteria("a", SqlOperator.IS_NOT_NULL);
        Criteria criteria2 = new Criteria("a", SqlOperator.IS_NOT_NULL);
        Set<Criteria> criterias = new HashSet<Criteria>();
        criterias.add(criteria);
        criterias.add(criteria2);
        Assert.assertEquals(criteria, criteria2);
        Assert.assertTrue(criterias.size() == 1);
    }

    @Test
    public void testContain() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("a", "b");
        example.equalsTo("a", "b");
        Assert.assertEquals(1, example.getCriterias().size());
        Assert.assertTrue(example.containCriteria(new Criteria("a", SqlOperator.EQ,
                "b")));
    }
}
