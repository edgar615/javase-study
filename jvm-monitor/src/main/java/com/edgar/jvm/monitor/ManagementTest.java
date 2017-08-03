package com.edgar.jvm.monitor;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/**
 * Created by Edgar on 2017/8/3.
 *
 * @author Edgar  Date 2017/8/3
 */
public class ManagementTest {
  public static void main(String[] args) {
    OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();
    System.out.println(mxBean.getSystemLoadAverage());

    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    System.out.println(threadMXBean.getAllThreadIds());
    System.out.println(threadMXBean.getCurrentThreadCpuTime());
    System.out.println(threadMXBean.getCurrentThreadUserTime());

    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    System.out.println(memoryMXBean.getHeapMemoryUsage());
    System.out.println(memoryMXBean.getNonHeapMemoryUsage());
    System.out.println(memoryMXBean.getObjectPendingFinalizationCount());

    for (MemoryManagerMXBean memoryManagerMXBean : ManagementFactory.getMemoryManagerMXBeans()) {
      System.out.println(memoryManagerMXBean.getName());
    }


    NotificationListener notificationListener = new NotificationListener() {
      public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo
                                                  .GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from
                  ((CompositeData) notification.getUserData());
          System.out.println(gcInfo.getGcName());
          System.out.println(gcInfo.getGcInfo().getMemoryUsageBeforeGc());
          System.out.println(gcInfo.getGcInfo().getMemoryUsageAfterGc());

          for (MemoryUsage memoryUsage : gcInfo.getGcInfo().getMemoryUsageBeforeGc().values()) {
            System.out.println(memoryUsage.getUsed());
          }
        }
      }
    };
    for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
      NotificationEmitter emitter = (NotificationEmitter) gcBean;
      emitter.addNotificationListener(notificationListener, null, null);
    }
    while (true) {

    }
  }
}
