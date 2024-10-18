package com.egon89;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * <a href="https://dev.java/learn/api/streams/reducing/">dev.java</a>
 */
public class Reducing {
  public static void main(String[] args) {
    classicForLoop();
    classicForLoopWithBinaryOperator();
  }

  private static void classicForLoop() {
    List<Integer> ints = List.of(3, 6, 2, 1);
    int sum = ints.getFirst();
    for (int i = 1; i < ints.size(); i++) {
      sum += ints.get(i);
    }
    System.out.printf("sum: %d%n", sum); // 12
  }


  private static void classicForLoopWithBinaryOperator() {
    List<Integer> ints = List.of(3, 6, 2, 1);
    BinaryOperator<Integer> sumOperator = Integer::sum; // (a, b) -> a + b
    int sum = ints.getFirst();
    for (int i = 1; i < ints.size(); i++) {
      sum = sumOperator.apply(sum, ints.get(i));
    }
    System.out.printf("sum with binary operator: %d%n", sum); // 12

    BinaryOperator<Integer> maxOperator = BinaryOperator.maxBy(Integer::compareTo);
    int max = ints.getFirst();
    for (int i = 1; i < ints.size(); i++) {
      max = maxOperator.apply(max, ints.get(i));
    }
    System.out.printf("max with binary operator: %d%n", max); // 6
  }
}
