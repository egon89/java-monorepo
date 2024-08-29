package com.egon89;

import java.util.stream.IntStream;

/**
 * <a href="https://dev.java/learn/refactoring-to-functional-style/loopswithsteps/#break">dev.java</a>
 */
public class UnboundedIterationWithBreak {
  public static void main(String[] args) {
    for (int i = 0;; i = i + 3) {
      if (i > 20) break;

      System.out.println(i);
    }

    IntStream.iterate(0, i -> i + 3)
        .takeWhile(i -> i < 20)
        .forEach(System.out::println);

    // or just
    IntStream.iterate(0, i -> i < 20, i -> i + 3)
        .forEach(System.out::println);
  }
}
