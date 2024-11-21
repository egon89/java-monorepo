package com.egon89;

import java.util.Collection;
import java.util.List;

public class StreamCharacteristics {
  public static void main(String[] args) {
    sorted();
  }

  private static void sorted() {
    Collection<String> stringCollection = List.of("one", "two", "two", "three", "four", "five");

    List<String> strings = stringCollection.stream().sorted().toList();
    System.out.println(strings); // sorted
    List<String> filteredStrings = strings.stream().filter(s -> s.length() < 5).toList();
    System.out.println(filteredStrings); // sorted
    List<Integer> lengths = filteredStrings.stream().map(String::length).toList();
    System.out.println(lengths); // not sorted

    /*
      [five, four, one, three, two, two]
      [five, four, one, two, two]
      [4, 4, 3, 3, 3] -> the map or flatmap removes the sorted characteristics from the resulting stream
    */
  }
}
