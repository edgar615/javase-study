/**
 * Created by Edgar on 2016/3/17.
 *
 * @author Edgar  Date 2016/3/17
 */
public class IDMain {
  public static void main(String[] args) {
//    instagram
//        使用41 bit来存放时间，精确到毫秒，可以使用41年。
//    使用13 bit来存放逻辑分片ID。
//    使用10 bit来存放自增长ID，意味着每台机器，每毫秒最多可以生成1024个ID
    int nodes = 10;//分片数
    int userId = 31341;
    int seqId = 5001;
    long id = System.currentTimeMillis();// - epoch;
    System.out.println("毫秒：" + id);
    //用左移方法填充最左边41位值是
    id = id << 22;
    System.out.println("毫秒数填充最左边的41位:" + id);
//    再把分片ID放到时间里，假定用户ID是31341，有2000个逻辑分片，则分片ID是31341 % 2000 -> 1341：
    //左移填充剩下的13位
    id |= (userId % nodes) << 12;
    System.out.println("分片ID:" + (userId % nodes));
    System.out.println("分片ID填充中间的13位:" + id);
    //最后，把自增序列放ID里，假定前一个序列是5000,则新的序列是5001：
    //填充剩下的10位
    id |= (seqId % 1024);
    //最终主键
    System.out.println("自增ID:" + (seqId % 1024));
    System.out.println("自增填充最后10位:" + id);

    //解析数据
    //右移23位，得到毫秒数
    long mills = id >> 22;
    System.out.println("右移23位得到毫秒：" + mills);
    long sharedIdWithInc = id ^ (mills << 22);
    System.out.println("原ID与毫秒数左移异或得到分片ID和自增主键：" + sharedIdWithInc);
    long sharedId = sharedIdWithInc >> 12;
    System.out.println("右移10位得到分片ID：" + sharedId);
    long incId = sharedIdWithInc ^ (sharedId << 12);
    System.out.println("sharedIdWithInc与分片ID左移异或得到自增主键：" + incId);
  }
}
