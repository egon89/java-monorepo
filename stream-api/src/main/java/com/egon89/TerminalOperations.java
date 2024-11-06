package com.egon89;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TerminalOperations {
  public static void main(String[] args) {
    getMaxWord();
    getMinWord();
    checkIfMatchPredicate();
  }

  private static void getMaxWord() {
    Stream<String> strings = Stream.of("one", "two", "three", "four");
    String longest = strings.max(Comparator.comparing(String::length)).orElseThrow();

    System.out.printf("longest: %s%n", longest); // longest: three
  }

  private static void getMinWord() {
    Stream<String> strings = Stream.of("one", "two", "three", "four");
    String smallest = strings.min(Comparator.comparing(String::length)).orElseThrow();

    System.out.printf("smallest: %s%n", smallest); // smallest: two
  }

  private static void checkIfMatchPredicate() {
    List<String> strings =
        List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");

    boolean noBlank = strings.stream().allMatch(Predicate.not(String::isBlank));
    boolean oneEqualsThan3 = strings.stream().anyMatch(s -> s.length() == 3);
    boolean anyGreaterThan4 = strings.stream().noneMatch(s -> s.length() > 4);

    System.out.printf("no blank: %b%n", noBlank); // true
    System.out.printf("oneEqualsThan3: %b%n", oneEqualsThan3); // true
    System.out.printf("anyGreaterThan4: %b%n", anyGreaterThan4); // false
  }
}
