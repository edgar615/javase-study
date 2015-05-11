package coll;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Administrator on 2015/5/11.
 */
public class IterablesTest {

    //返回iterable的不可变视图
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableIterable() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterable<Integer> iterable = Iterables.unmodifiableIterable(list);
        iterable.iterator().remove();
        Assert.fail();
    }

    @Test
    public void testSize() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(4, Iterables.size(list));
    }

    @Test
    public void testContains() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertTrue(Iterables.contains(list, 3));
    }

    @Test
    public void testRemoveAll() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.removeAll(list, Lists.newArrayList(2, 3));
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testRetainAll() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.retainAll(list, Lists.newArrayList(2, 3, 4, 5));
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testRemoveIf() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.removeIf(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testRemoveFirstMatch() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertTrue(Iterables.elementsEqual(Lists.newArrayList(1, 2, 3, 4), Lists.newArrayList(1, 2, 3, 4)));
        System.out.println(Iterables.toString(list));
    }

    //获取iterable中唯一的元素，如果iterable为空或有多个元素，则快速失败
    @Test(expected = IllegalArgumentException.class)
    public void testGetOnlyElement() {
        Iterables.getOnlyElement(Lists.newArrayList(1));
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.getOnlyElement(list);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOnlyElementDefault() {
        Assert.assertEquals(Iterables.getOnlyElement(Lists.newArrayList(), 3), 3, 0);
        Assert.assertEquals(Iterables.getOnlyElement(Lists.newArrayList(1), 3), 1, 0);
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.getOnlyElement(list);
        Assert.fail();
    }

    @Test
    public void testToArray() {
        System.out.println(Iterables.toArray(Lists.newArrayList(1, 2, 3, 4), Integer.class));
    }

    @Test
    public void testAddAll() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterables.addAll(list, Lists.newArrayList(5, 6, 7));
        Assert.assertEquals(7, list.size());
    }

    // 	返回对象在iterable中出现的次数
    @Test
    public void testFrequency() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 2, 3, 4, 5);
        Assert.assertEquals(1, Iterables.frequency(list, 1));
        Assert.assertEquals(2, Iterables.frequency(list, 2));
    }

    //将迭代器设为环状
    @Test
    public void testCycle() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterable<Integer> iterable = Iterables.cycle(list);
        Iterator<Integer> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            if (count > 9) {
                break;
            }
            System.out.println(iterator.next());
        }
        Assert.assertEquals(10, count);
    }

    @Test
    public void testCycle2() {
        Iterable<Integer> iterable = Iterables.cycle(1, 2, 3, 4);
        Iterator<Integer> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            if (count > 9) {
                break;
            }
            System.out.println(iterator.next());
        }
        Assert.assertEquals(10, count);
    }

    //串联多个iterables的懒视图*
    @Test
    public void testConcat() {
        Iterable<Integer> iterable = Iterables.concat(Lists.newArrayList(1, 2, 3, 4), Lists.newArrayList(1, 2, 3, 4));
        Assert.assertEquals(8, Iterables.size(iterable));
        Assert.assertEquals(6, Iterables.size(Iterables.concat(Lists.newArrayList(1), Lists.newArrayList(1, 2), Lists.newArrayList(1, 2, 3))));
    }

    //把iterable按指定大小分割，得到的子集都不能进行修改操作
    @Test(expected = UnsupportedOperationException.class)
    public void testPartition() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Iterable<List<Integer>> iterable = Iterables.partition(list, 2);
        System.out.println(iterable);//[[1, 2], [3, 4], [5]]
        Iterables.get(iterable, 0).add(3);
        Assert.fail();
    }

    //把iterable按指定大小分割，最后不足的用null补齐，得到的子集都不能进行修改操作
    @Test(expected = UnsupportedOperationException.class)
    public void testPaddedPartition() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Iterable<List<Integer>> iterable = Iterables.paddedPartition(list, 2);
        System.out.println(iterable);//[[1, 2], [3, 4], [5, null]]
        Iterables.get(iterable, 0).add(3);
        Assert.fail();
    }

    @Test
    public void testFilter() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Iterable<Integer> iterable = Iterables.filter(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        Assert.assertEquals(2, Iterables.size(iterable));
    }

    @Test
    public void testFilterClass() {
        List<Object> list = Lists.newArrayList(1, 2, 3, Lists.newLinkedList(), Sets.newHashSet(), Lists.newArrayList());
        Iterable<Integer> iterable = Iterables.filter(list, Integer.class);
        Assert.assertEquals(3, Iterables.size(iterable));
    }

    @Test
    public void testAny() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Assert.assertTrue(Iterables.any(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 5 == 0;
            }
        }));

        Assert.assertFalse(Iterables.any(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 6;
            }
        }));
    }

    @Test
    public void testAll() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Assert.assertTrue(Iterables.all(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 6;
            }
        }));

        Assert.assertFalse(Iterables.all(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 5;
            }
        }));

        //空
        Assert.assertTrue(Iterables.all(Lists.newArrayList(), new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 6;
            }
        }));
    }

    //返回满足条件的第一个元素
    @Test(expected = NoSuchElementException.class)
    public void testFind() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Assert.assertEquals(2, Iterables.find(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }), 0);
        Iterables.find(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 6;
            }
        });
        Assert.fail();
    }

    @Test
    public void testFindDefault() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Assert.assertEquals(2, Iterables.find(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }, 3), 0);
        Assert.assertEquals(2, Iterables.find(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }, 2), 0);
    }

    @Test
    public void testIndexOf() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Assert.assertEquals(1, Iterables.indexOf(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }), 0);
        Assert.assertEquals(-1, Iterables.indexOf(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 6;
            }
        }), 0);
    }

    //转换
    @Test
    public void testTransform() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Iterable<String> iterable = Iterables.transform(list, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return "(" + input + ") ";
            }
        });
        System.out.println(Iterables.toString(iterable));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(2, Iterables.get(list, 1), 0);
        Assert.assertEquals(2, Iterables.get(list, 5), 0);
        Assert.fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetDefault() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(2, Iterables.get(list, 1, 3), 0);
        Assert.assertEquals(3, Iterables.get(list, 5, 3), 0);
        Assert.assertEquals(3, Iterables.get(list, -1, 3), 0);
        Assert.fail();
    }

    //返回iterable的第一个元素，若iterable为空则返回默认值
    @Test
    public void testGetFirst() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(1, Iterables.getFirst(list, 5), 0);
        Assert.assertEquals(5, Iterables.getFirst(Lists.newArrayList(), 5), 0);
    }

    //返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException
    @Test(expected = NoSuchElementException.class)
    public void testGetLast() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(4, Iterables.getLast(list), 0);
        Assert.assertEquals(5, Iterables.getLast(Lists.newArrayList(), 5), 0);
        Iterables.getLast(Lists.newArrayList());
        Assert.fail();
    }

    //跳过前面几个元素
    @Test
    public void testSkip() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(1, Iterables.size(Iterables.skip(list, 3)));
    }

    //返回前几个元素
    @Test
    public void testLimit() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Assert.assertEquals(2, Iterables.size(Iterables.limit(list,2)));
        System.out.println(Iterables.limit(list, 2));
        Assert.assertEquals(4, Iterables.size(Iterables.limit(list,5)));
    }
}
