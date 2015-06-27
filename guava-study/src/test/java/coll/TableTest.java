package coll;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by edgar on 15-6-27.
 */
public class TableTest {

    @Test
    public void testHashTable() {
        Table<Integer, Integer, String> table = HashBasedTable.create();
        table.put(1,4,"Rook");
        table.put(1,5,"Knight");
        table.put(1,6,"Bishop");
        table.put(2,5,"F");
        table.put(2,6,"F");
        table.put(2,7,"M");
        System.out.println(table);//{1={4=Rook, 5=Knight, 6=Bishop}, 2={5=F, 6=F, 7=M}}
        System.out.println(table.row(1));//{4=Rook, 5=Knight, 6=Bishop}
        System.out.println(table.row(2));//{5=F, 6=F, 7=M}
        System.out.println(table.column(5));//{1=Knight, 2=F}

        Assert.assertTrue(table.contains(1,4));
        Assert.assertFalse(table.contains(1, 1));
        Assert.assertTrue(table.containsColumn(5));
        Assert.assertFalse(table.containsColumn(2));
        Assert.assertTrue(table.containsRow(1));
        Assert.assertFalse(table.containsRow(3));
        Assert.assertTrue(table.containsValue("Rook"));
        Assert.assertFalse(table.containsValue("Rook2"));

        table.remove(1,3);
        System.out.println(table);//{1={4=Rook, 5=Knight, 6=Bishop}, 2={5=F, 6=F, 7=M}}
        table.remove(1,6);
        System.out.println(table);//{1={4=Rook, 5=Knight}, 2={5=F, 6=F, 7=M}}
        System.out.println(table.get(2,6));//F
        System.out.println(table.get(3,4));//null

        Map<Integer,String> columnMap = table.column(6);
        Map<Integer,String> rowMap = table.row(2);
        System.out.println(columnMap);//{2=F}
        System.out.println(rowMap);//{5=F, 6=F, 7=M}
    }
}
