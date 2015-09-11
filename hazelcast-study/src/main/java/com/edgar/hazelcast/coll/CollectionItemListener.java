package com.edgar.hazelcast.coll;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CollectionItemListener implements ItemListener {
    @Override
    public void itemAdded(ItemEvent itemEvent) {
        System.out.println("ItemListener – itemAdded:" + itemEvent.getItem());
    }

    @Override
    public void itemRemoved(ItemEvent itemEvent) {
        System.out.println("ItemListener – itemRemoved:" + itemEvent.getItem());
    }
}
