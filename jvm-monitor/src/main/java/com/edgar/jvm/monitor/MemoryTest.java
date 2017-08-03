package com.edgar.jvm.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;

/**
 * Created by Edgar on 2017/8/3.
 *
 * @author Edgar  Date 2017/8/3
 */
public class MemoryTest {
  public static void main(String[] args) {
    System.out.println(getEden().getUsage().getMax());
  }

  private static MemoryPoolMXBean getEden() {
    for (final MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
      System.out.println(memoryPool.getName());
      System.out.println("init:" + memoryPool.getUsage().getInit() );
      System.out.println("max:" + memoryPool.getUsage().getMax() );
      System.out.println("used:" + memoryPool.getUsage().getUsed() );
      System.out.println("committed:" + memoryPool.getUsage().getCommitted() );
      // name est "Perm Gen" ou "PS Perm Gen" (32 vs 64 bits ?)
      if (memoryPool.getName().endsWith("PS Eden Space")) {
        return memoryPool;
      }
    }
    return null;
  }
}
