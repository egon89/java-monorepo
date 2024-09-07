package com.egon89;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    System.out.println("Stream API module");

    List<String> strings = List.of("one", "two", "three", "four");
    // grouping by string length
    Map<Integer, Long> map = strings.stream()
        .collect(Collectors.groupingBy(String::length, Collectors.counting()));

    map.forEach((key, value) -> System.out.printf("%d :: %d%n", key, value));
    /*
      3 :: 2
      4 :: 1
      5 :: 1
    */

    IntSummaryStatistics statistics = IntStream.rangeClosed(0, 1237)
        .filter(value -> value % 2 == 0)
        .summaryStatistics();

    System.out.printf("Max: %d%n", statistics.getMax());
    System.out.printf("Min: %d%n", statistics.getMin());
    System.out.printf("Count: %d%n", statistics.getCount());
    System.out.printf("Avg: %.2f%n", statistics.getAverage());
    System.out.printf("Sum: %d%n", statistics.getSum());
    /*
      Max: 1236
      Min: 0
      Count: 619
      Avg: 618.00
      Sum: 382542
    */
  }
}