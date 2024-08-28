package com.egon89;

import java.util.stream.IntStream;

/**
 * <a href="https://dev.java/learn/refactoring-to-functional-style/simpleloops/">dev.java</a>
 */
public class SimpleForLoops {
  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
      System.out.println(i);
    }

    IntStream.range(0, 5).forEach(System.out::println);

    // include the ending value
    for (int i = 0; i <= 5; i++) {
      System.out.println(i);
    }

    IntStream.rangeClosed(0, 5).forEach(System.out::println);
  }
}
