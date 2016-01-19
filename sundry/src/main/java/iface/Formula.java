package iface;

interface Formula {
  double calculate(int a);

  //  Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展方法，默认方法sqrt将在子类上可以直接使用
  default double sqrt(int a) {
    return Math.sqrt(a);
  }
} 