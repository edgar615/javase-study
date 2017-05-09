package com.edgar.curator;

import java.math.BigInteger;

/**
 * Created by Edgar on 2016/3/17.
 *
 * @author Edgar  Date 2016/3/17
 */
public class IDMain2 {
  public static void main(String[] args) {
    System.out.println(Long.toBinaryString(System.currentTimeMillis()).length());
    //Boundary Flake
//128 bits
    //64 bits		Timestamp				millisecond precision, 1970 epoch
//    48 bits		MAC address
//    16 bits		Sequence number
    int userId = 31341;
    int seqId = 5001;
    long id = System.currentTimeMillis();// - epoch;
    BigInteger bId = new BigInteger(id + "");
    System.out.println("毫秒：" + bId);
    //用左移方法填充最左边64位值
//    id = id << (128 - 64);
    bId = bId.shiftLeft(128 - 64);

    System.out.println("毫秒数填充最左边的64位:" + bId);
    //左移填充剩下的48位
    long mac = Long.parseLong("18c8e7148bfb", 16);
    System.out.println("mac:"  + mac);
    bId = bId.or(new BigInteger(mac + "").shiftLeft(128-64-48));
//    id |= mac << (128 - 64 - 48);
    System.out.println("mac填充中间的48位:" + bId);
//    //填充剩下的16位
    bId = bId.or(new BigInteger((seqId % 65536) + ""));
//    id |= (seqId % 65536);
//    //最终主键
    System.out.println("自增ID:" + (seqId % 65536));
    System.out.println("自增填充最后10位:" + id);
//
    //解析数据
    //右移64位，得到毫秒数
    BigInteger mills = bId.shiftRight(128 -64);
    System.out.println("右移64位得到毫秒：" + mills);
    BigInteger sharedIdWithInc = bId.xor(mills.shiftLeft(64));
    System.out.println("原ID与毫秒数左移异或得到分片ID和自增主键：" + sharedIdWithInc);
    BigInteger sharedId = sharedIdWithInc.shiftRight(16);
    System.out.println("右移10位得到分片ID：" + sharedId);
    BigInteger incId = sharedIdWithInc.xor(sharedId.shiftLeft(16));
    System.out.println("sharedIdWithInc与分片ID左移异或得到自增主键：" + incId);
  }
}
