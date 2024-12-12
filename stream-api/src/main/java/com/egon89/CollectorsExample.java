package com.egon89;

import java.util.LinkedList;
import java.util.List;
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
}
