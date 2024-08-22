package com.egon89;

import java.util.Objects;

/**
 * <a href="https://dev.java/learn/pattern-matching/#instanceof">dev.java</a>
 */
public class PatternMatchingForInstanceOf {
  public static void main(String[] args) {
    final var m = new MatchingAnyObjectToATypeWithInstanceOf();
    m.print("John");
    m.print(30);

    final var p1 = new Point(1, 2);
    final var p2 = new Point(1.01, 2.02);
    final var p3 = new Point(1, 2);

    System.out.println(p1.equals(p2)); // false
    System.out.println(p1.equals(p3)); // true
  }

  private static class MatchingAnyObjectToATypeWithInstanceOf {
    public void print(Object o) {
      if (o instanceof String s) {
        System.out.printf("This is a String of length %d%n", s.length());
      } else {
        System.out.println("This is not a String");
      }
    }
  }

  private record Point(double x, double y) {
    @Override
    public boolean equals(Object o) {
      return o instanceof Point p
          && Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
