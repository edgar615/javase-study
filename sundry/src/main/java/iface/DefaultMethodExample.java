package iface;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class DefaultMethodExample {

  public static void main(String[] args) {
    Formula formula = formula();
    System.out.println(formula.calculate(1));
    System.out.println(formula.sqrt(64));

    //Lambda表达式中是无法访问到默认方法的，以下代码将无法编译
//    formula = (a) -> sqrt(a * 100);
  }

  public static Formula formula() {
    return new Formula() {
      @Override
      public double calculate(int a) {
        return sqrt(a * 100);
      }
    };
  }
}
