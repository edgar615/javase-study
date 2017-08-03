package com.edgar.jvm.monitor;

import java.util.concurrent.TimeUnit;

/**
 * Created by Edgar on 2017/8/3.
 *
 * @author Edgar  Date 2017/8/3
 */
public class DeadLockMain {
  public static void main(String[] args) {
    DeadlockDetector deadlockDetector =
            new DeadlockDetector(new DeadlockConsoleHandler(), 5, TimeUnit.SECONDS);
    deadlockDetector.start();

    final Object lock1 = new Object();
    final Object lock2 = new Object();

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (lock1) {
          System.out.println("Thread1 acquired lock1");
          try {
            TimeUnit.MILLISECONDS.sleep(500);
          } catch (InterruptedException ignore) {
          }
          synchronized (lock2) {
            System.out.println("Thread1 acquired lock2");
          }
        }
      }

    });
    thread1.start();

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (lock2) {
          System.out.println("Thread2 acquired lock2");
          synchronized (lock1) {
            System.out.println("Thread2 acquired lock1");
          }
        }
      }
    });
    thread2.start();
  }
}
