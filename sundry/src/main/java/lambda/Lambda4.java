package lambda;

class Lambda4 {
  static int outerStaticNum;

  int outerNum;

  //lambda内部对于实例的字段以及静态变量是即可读又可写。该行为和匿名对象是一致的：
  void testScopes() {
    Converter<Integer, String> stringConverter1 = (from) -> {
      outerNum = 23;
      return String.valueOf(from);
    };

    Converter<Integer, String> stringConverter2 = (from) -> {
      outerStaticNum = 72;
      return String.valueOf(from);
    };
  }
} 