package gc;

/**
 * -Xmx20M -Xms20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintGCDetails
 *
 * <pre>
 *     Heap
 PSYoungGen      total 9216K, used 6320K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 eden space 8192K, 77% used [0x00000000ff600000,0x00000000ffc2c090,0x00000000ffe00000)
 from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400020,0x00000000ff600000)
 Metaspace       used 3201K, capacity 4494K, committed 4864K, reserved 1056768K
 class space    used 348K, capacity 386K, committed 512K, reserved 1048576K
 * </pre>
 */
public class MaxTenuringThreshold {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
