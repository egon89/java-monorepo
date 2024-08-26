package com.egon89;

import java.math.BigDecimal;
import java.util.List;

/**
 * <a href="https://dev.java/learn/pattern-matching/#record">dev.java</a>
 */
public class RecordPatternMatching {
  public static void main(String[] args) {
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

  private record Point(int x, int y) {
    public Point(int x) {
      this(x, 0);
    }
  }
}
