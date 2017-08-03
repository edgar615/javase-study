package com.edgar.jvm.monitor;

import java.lang.management.ManagementFactory;

/**
 * Created by Edgar on 2017/8/3.
 *
 * @author Edgar  Date 2017/8/3
 */
public class SunManagementTest {
  public static void main(String[] args) {
    com.sun.management.OperatingSystemMXBean os
            =
            (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    long physicalMemorySize = os.getTotalPhysicalMemorySize();
    System.out.println(physicalMemorySize);
    long freePhysicalMemory = os.getFreePhysicalMemorySize();
    System.out.println(freePhysicalMemory);
    long freeSwapSize = os.getFreeSwapSpaceSize();
    System.out.println(freeSwapSize);
    long commitedVirtualMemorySize = os.getCommittedVirtualMemorySize();
    System.out.println(commitedVirtualMemorySize);

    System.out.println(os.getProcessCpuLoad());
    System.out.println(os.getProcessCpuTime());
    System.out.println(os.getSystemCpuLoad());
    System.out.println(os.getTotalPhysicalMemorySize());


//    long    getCommittedVirtualMemorySize()
//    Returns the amount of virtual memory that is guaranteed to be available to the running
// process in bytes, or -1 if this operation is not supported.
//
//    long    getFreePhysicalMemorySize()
//    Returns the amount of free physical memory in bytes.
//
//    long    getFreeSwapSpaceSize()
//    Returns the amount of free swap space in bytes.
//
//    double  getProcessCpuLoad()
//    Returns the "recent cpu usage" for the Java Virtual Machine process.
//
//    long    getProcessCpuTime()
//    Returns the CPU time used by the process on which the Java virtual machine is running in
// nanoseconds.
//
//    double  getSystemCpuLoad()
//    Returns the "recent cpu usage" for the whole system.
//
//    long    getTotalPhysicalMemorySize()
//    Returns the total amount of physical memory in bytes.
//
//    long    getTotalSwapSpaceSize()
//    Returns the total amount of swap space in bytes.

  }
}
