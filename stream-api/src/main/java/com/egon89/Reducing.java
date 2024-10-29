package com.egon89;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/api/streams/reducing/">dev.java</a>
 */
public class Reducing {
  public static void main(String[] args) {
    classicForLoop();
    classicForLoopWithBinaryOperator();
    parallelSimulation();
    reducingWithAnIdentityElement();
    reducingWithNoIdentityElement();
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

  // The Stream API splits the source data to process each chunk
  // finally, the chunks are merged to get the final result
  private static void parallelSimulation() {
    List<Integer> ints = List.of(3, 6, 2, 1);
    BinaryOperator<Integer> sumOperator = Integer::sum;

    int result1 = reduce(ints.subList(0, 2), sumOperator);
    int result2 = reduce(ints.subList(2, 4), sumOperator);

    int result = sumOperator.apply(result1, result2);
    System.out.printf("Parallel simulation: %d%n", result); // 12
  }

  private static int reduce(List<Integer> ints, BinaryOperator<Integer> sum) {
    int result = ints.get(0);
    for (int index = 1; index < ints.size(); index++) {
      result = sum.apply(result, ints.get(index));
    }
    return result;
  }

  private static void reducingWithAnIdentityElement() {
    List<Integer> ints = List.of(3, 6, 2, 1);
    BinaryOperator<Integer> sum = Integer::sum;
    int identity = 0;

    int result = identity;
    for (int i: ints) {
      result = sum.apply(result, i);
    }

    System.out.printf("reducing with an identity element: %d%n", result);

    // stream format
    int result2 = ints.stream().reduce(identity, Integer::sum);
    System.out.printf("reducing with an identity element: %d%n", result2);
  }

  private static void reducingWithNoIdentityElement() {
    // the return is an optional when there is no identity element
    Stream<Integer> ints = Stream.of(2, 8, 1, 5, 3);
    Optional<Integer> optionalMax = ints.reduce((x, y) -> x > y ? x : y);

    System.out.printf("Max: %d%n", optionalMax.orElse(0)); // 8

  }
}
