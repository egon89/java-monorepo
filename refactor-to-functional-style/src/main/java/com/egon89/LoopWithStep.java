package com.egon89;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * <a href="https://dev.java/learn/refactoring-to-functional-style/loopswithsteps/#imperativetofunctional">dev.java</a>
 */
public class LoopWithStep {
  public static void main(String[] args) {
    for (int i = 0; i < 15; i = i + 3) {
      System.out.println(i);
    }

    IntPredicate lessThanFifteen = i -> i < 15;
    IntUnaryOperator stepByThree = i -> i + 3;
    IntStream.iterate(0, lessThanFifteen, stepByThree)
        .forEach(System.out::println);

  }
}
