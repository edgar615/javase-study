import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/14.
 */
public class JiFen {

    public static void main(String[] args) {
        //总积分
        BigDecimal total = new BigDecimal("135.4");
        //升级需要积分
        BigDecimal needed = new BigDecimal("110");
        //多余的积分
        BigDecimal surplus = total.subtract(needed);

        //项目积分
        BigDecimal project = new BigDecimal("66.9");
        //职位积分
        BigDecimal position = new BigDecimal("31.5");

        //项目积分和职位积分，
        List<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal("2").add(new BigDecimal("0.5")));
        list.add(new BigDecimal("3").add(new BigDecimal("1.0")));
        list.add(new BigDecimal("1.5").add(new BigDecimal("0.5")));
        list.add(new BigDecimal("10.4").add(new BigDecimal("1.5")));
        list.add(new BigDecimal("10").add(new BigDecimal("6")));
        list.add(new BigDecimal("5").add(new BigDecimal("3.0")));
        list.add(new BigDecimal("34.8").add(new BigDecimal("18")));
        list.add(new BigDecimal("0.2").add(new BigDecimal("1")));

        //项目积分和职位积分总和
        BigDecimal sum = new BigDecimal("0");
        for (BigDecimal b : list) {
            sum = sum.add(b);
        }
        if (project.add(position).compareTo(sum) != 0) {
            throw new RuntimeException("积分输入有误");
        }

        long l = 1306061211;
        System.out.println(new Date(l));
        System.out.println(System.currentTimeMillis());
    }
}
