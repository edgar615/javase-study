package com.edgar.curator;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by Edgar on 2016/3/17.
 *
 * @author Edgar  Date 2016/3/17
 */
public class IDMain3 {
  public static void main(String[] args) {
    //64位bit，32位时间戳+32位自增序列
    int seqId = 5001;
    long id = System.currentTimeMillis();// - epoch;
    System.out.println("毫秒：" + id);
    //用左移方法填充最左边64位值
    id = id << 22;

    System.out.println("毫秒数填充最左边的32位:" + id);
    //左移填充剩下的48位
    id |= seqId % 8388608;
    System.out.println("自增ID:" + (seqId % 8388608));
    System.out.println("最终的ID:" + id);
//
    //解析数据
    //右移32位，得到毫秒数
    long mills = id >> 22;
    System.out.println("右移32位得到毫秒：" + mills);
    long incId = id ^ (mills << 22);
    System.out.println("原ID与毫秒数左移异或得到分片ID和自增主键：" + incId);
  }
}
