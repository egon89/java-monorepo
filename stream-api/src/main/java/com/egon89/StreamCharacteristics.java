package com.egon89;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class StreamCharacteristics {
  public static void main(String[] args) {
    Collection<String> stringCollection = List.of("one", "two", "two", "three", "four", "five");
    sorted(stringCollection);
    distinctStream(stringCollection);
  }

  private static void sorted(Collection<String> stringCollection) {
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

  private static void distinctStream(Collection<String> stringCollection) {
    List<String> strings = stringCollection.stream().distinct().toList();
    System.out.println(strings); // [one, two, three, four, five]

    List<String> filteredStrings =
        stringCollection.stream()
            .distinct()
            .filter(s -> s.length() < 5) // remove elements cannot create duplicates (it's still distinct)
            .toList();
    System.out.println(filteredStrings); // [one, two, four, five]

    List<Integer> lenghtList = stringCollection.stream()
        .distinct()
        .filter(s -> s.length() < 5)
        .map(String::length) // distinct is lost when mapping or flatmapping occurs
        .toList();
    System.out.println(lenghtList); // [3, 3, 4, 4]
  }
}
