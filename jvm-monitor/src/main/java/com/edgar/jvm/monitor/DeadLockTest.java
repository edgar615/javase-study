package com.edgar.jvm.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by Edgar on 2017/8/3.
 *
 * @author Edgar  Date 2017/8/3
 */
public class DeadLockTest {
  public static void main(String[] args) {
    final Shared s1 = new Shared();

    final Shared s2 = new Shared();

    Thread t1 = new Thread() {
      public void run() {
        s1.methodOne(s2);
      }
    };

    Thread t2 = new Thread() {
      @Override
      public void run() {
        s2.methodTwo(s1);
      }
    };

    t1.start();

    t2.start();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    long ids[] = bean.findMonitorDeadlockedThreads();

    if (ids != null) {
      ThreadInfo threadInfo[] = bean.getThreadInfo(ids);

      for (ThreadInfo threadInfo1 : threadInfo) {
        System.out.println(threadInfo1.getThreadId());    //Prints the ID of deadlocked thread

        System.out.println(threadInfo1.getThreadName());  //Prints the name of deadlocked thread

        System.out.println(threadInfo1
                                   .getLockName());    //Prints the string representation of an
        // object for which thread has entered into deadlock.

        System.out.println(threadInfo1
                                   .getLockOwnerId());  //Prints the ID of thread which currently
        // owns the object lock

        System.out.println(threadInfo1
                                   .getLockOwnerName());  //Prints name of the thread which
        // currently owns the object lock.
      }
    } else {
      System.out.println("No Deadlocked Threads");
    }
  }

  static class Shared {
    synchronized void methodOne(Shared s) {
      Thread t = Thread.currentThread();

      System.out.println(t.getName() + "is executing methodOne...");

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println(t.getName() + "is calling methodTwo...");

      s.methodTwo(this);

      System.out.println(t.getName() + "is finished executing methodOne...");
    }

    synchronized void methodTwo(Shared s) {
      Thread t = Thread.currentThread();

      System.out.println(t.getName() + "is executing methodTwo...");

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println(t.getName() + "is calling methodOne...");

      s.methodOne(this);

      System.out.println(t.getName() + "is finished executing methodTwo...");
    }
  }
}
