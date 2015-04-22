package gc;

/**
 * -Xmn10M -Xms20M -Xmx20M  -verbose:gc -XX:ServivorRatio=8
 *
 * <pre>
 *     Heap
 PSYoungGen      total 9216K, used 8112K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 eden space 8192K, 99% used [0x00000000ff600000,0x00000000ffdec000,0x00000000ffe00000)
 from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
 Metaspace       used 3199K, capacity 4494K, committed 4864K, reserved 1056768K
 class space    used 348K, capacity 386K, committed 512K, reserved 1048576K
 * </pre>
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//出现一次Minor GC

    }

    public static void main(String[] args) {
        testAllocation();
    }
}
