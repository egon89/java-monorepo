package com.egon89;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <a href="https://dev.java/learn/api/streams/using-collectors/">dev.java</a>
 */
public class CollectorsExample {
  public static void main(String[] args) {
    collectorsFactory();
    countingWithACollector();
    collectingStringOfCharacters();
    partitioningElementsWithAPredicate();
    groupElementsInAMap();
  }

  private static void collectorsFactory() {
    System.out.println("> collectorsFactory");

    // List
    List<Integer> numbers =
        IntStream.range(0, 10)
            .boxed()
            .collect(Collectors.toList());
    System.out.println("numbers = " + numbers); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

    // Set: remove duplicate entries
    Set<Integer> evenNumbers =
        IntStream.range(0, 10)
            .map(number -> number / 2)
            .peek(System.out::println) // 0, 0, 1, 1, 2, 2, 3, 3, 4, 4
            .boxed()
            .collect(Collectors.toSet());
    System.out.println("evenNumbers = " + evenNumbers); // [0, 1, 2, 3, 4]

    // LinkedList
    LinkedList<Integer> linkedList =
        IntStream.range(0, 10)
            .boxed()
            .collect(Collectors.toCollection(LinkedList::new));
    System.out.println("linked list=" + linkedList); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
  }

  private static void countingWithACollector() {
    System.out.println("> countingWithACollector");

    var strings = List.of("one", "two", "three");
    long count = strings.stream().count();
    long countCollector = strings.stream().collect(Collectors.counting());
    System.out.printf("Counters: %d, %d%n", count, countCollector); // Counters: 3, 3
  }

  private static void collectingStringOfCharacters() {
    var value1 = IntStream.range(0, 10)
        .boxed()
        .map(Object::toString)
        .collect(Collectors.joining());
    System.out.println("value1: " + value1); // value1: 0123456789

    var value2 = IntStream.range(0, 10)
        .boxed()
        .map(Object::toString)
        .collect(Collectors.joining(", "));
    System.out.println("value2: " + value2); // value2: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9

    var value3 = IntStream.range(0, 10)
        .boxed()
        .map(Object::toString)
        .collect(Collectors.joining(", ", "{", "}"));
    System.out.println("value3: " + value3); // value3: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
  }

  private static void partitioningElementsWithAPredicate() {
    System.out.println("> partitioningElementsWithAPredicate");
    var strings =
        List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve");

    Map<Boolean, List<String>> map =
        strings.stream()
            .collect(Collectors.partitioningBy(s -> s.length() > 4));

    map.forEach((key, values) -> System.out.printf("key %b :: %s%n", key, values));
    // key false :: [one, two, four, five, six, nine, ten]
    // key true :: [three, seven, eight, eleven, twelve]
  }

  private static void groupElementsInAMap() {
    System.out.println("> groupElementsInAMap");
    var strings =
        List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve");

    Map<Integer, List<String>> map =
        strings.stream()
            .collect(Collectors.groupingBy(String::length));

    map.forEach((key, values) -> System.out.printf("key %d :: %s%n", key, values));

    // counting the values with downstream collector
    System.out.println(">> counter");
    Map<Integer, Long> mapCounter =
        strings.stream()
            .collect(Collectors.groupingBy(
                String::length, Collectors.counting()));

    mapCounter.forEach(
        (key, counter) -> System.out.printf("key %d :: %d%n", key, counter));

    // joining the values with downstream
    System.out.println(">> joining values");
    Map<Integer, String> mapJoining =
        strings.stream()
            .collect(Collectors.groupingBy(
                String::length, Collectors.joining(", ")));

    mapJoining.forEach((key, value) -> System.out.printf("key %d :: %s%n", key, value));
  }
}
