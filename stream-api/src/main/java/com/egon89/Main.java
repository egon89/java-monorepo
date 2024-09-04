package com.egon89;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
  }
}