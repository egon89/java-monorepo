package com.egon89;

import java.math.BigDecimal;
import java.util.List;

/**
 * <a href="https://dev.java/learn/pattern-matching/#record">dev.java</a>
 */
public class RecordPatternMatching {
  public static void main(String[] args) {
    usingInstanceOf();
    usingSwitch();
  }

  private static void usingInstanceOf() {
    final List<Object> list = List.of(
        "2", new Point(1, 2), new BigDecimal(10));
    list.forEach(o -> {
      if (o instanceof Point(int x, int y)) {
        System.out.println("Point instance");
      } else {
        System.out.println("Not a point instance");
      }
    });
    /*
      Not a point instance
      Point instance
      Not a point instance
    */

    Object o = new Point(0, 1);

    // this doesn't compile
    // we need to use the canonical constructor as record pattern
    // if (o instanceof Point(int x)) {}

    // type inference
    if (o instanceof Point(var x, var y)) {
      System.out.println("Point instance - type inference");
    }
  }

  private static void usingSwitch() {
    final var list = List.of(
        "2", new Box("2"), new Box(new BigDecimal(10)), new Box(1));
    list.forEach(o -> {
      switch(o) {
        case Box(String s) -> System.out.printf("Box contains the string %s%n", s);
        case Box(Integer i) -> System.out.printf("Box contains the integer %s%n", i);
        default -> System.out.println("Box contains something else");
      }
    });
    /*
      Box contains something else
      Box contains the string 2
      Box contains something else
      Box contains the integer 1
     */

    final var list2 = List.of(new BoxCS("a"), new BoxCS(new StringBuilder("b")));
    list2.forEach(o -> {
      switch(o) {
        case BoxCS(StringBuilder sb) -> System.out.printf("It contains a string builder %s%n", sb);
        case BoxCS(String s) -> System.out.printf("It contains a string %s%n", s);
        // case BoxCS(Integer i) -> System.out.println("compile error!"); // Compile error! Integer is not a CharSequence
        default -> System.out.println("It contains something else");
      }
    });
  }

  private record Point(int x, int y) {
    public Point(int x) {
      this(x, 0);
    }
  }

  private record Box(Object o) {}

  private record BoxCS(CharSequence cs) {}
}
