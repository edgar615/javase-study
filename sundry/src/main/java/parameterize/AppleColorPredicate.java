package parameterize;

class AppleColorPredicate implements ApplePredicate {
  public boolean test(Apple apple) {
    return "green".equals(apple.getColor());
  }
}