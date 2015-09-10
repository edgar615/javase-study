package gc;

/**
 * -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=1048576 -XX:+PrintGCDetails
 *
 * <pre>
 *     Heap
 PSYoungGen      total 9216K, used 1968K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 eden space 8192K, 24% used [0x00000000ff600000,0x00000000ff7ec068,0x00000000ffe00000)
 from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400010,0x00000000ff600000)
 Metaspace       used 3159K, capacity 4494K, committed 4864K, reserved 1056768K
 class space    used 340K, capacity 386K, committed 512K, reserved 1048576K
 * </pre>
 */
public class PretenureSizeThreshold {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation = new byte[8 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
