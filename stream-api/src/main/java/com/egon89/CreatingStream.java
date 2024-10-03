package com.egon89;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreatingStream {
  public static void main(String[] args) {
    createFromVarargOrArray();
    createFromSupplier();
    createFromUnaryOperator();
    createFromARangeOfNumbers();
    createFromRandomNumbers();
    createFromRegularExpression();
    /*
      > createFromVarargOrArray
      1
      2
      3
      one
      two
      three
      > createFromSupplier
      +
      +
      +
      > createFromUnaryOperator
      -
      --
      ---
      ----
      -----
      -
      --
      ---
      ----
      -----
      > createFromARangeOfNumbers
      i: 0, s: A
      i: 1, s: B
      i: 2, s: C
      i: 3, s: D
      i: 0, s: A
      i: 1, s: B
      i: 2, s: C
      i: 3, s: D
      i: 0, s: A
      i: 1, s: B
      [A, B, C, D, A, B, C, D, A, B]
      > createFromRandomNumbers
      Number of true: 773
      > createFromRegularExpression
      [For, there, is, good, news, yet, to, hear, and, fine, things, to, be, seen]
    */
  }

  private static void createFromVarargOrArray() {
    System.out.println("> createFromVarargOrArray");
    Stream<Integer> intStream = Stream.of(1, 2, 3);
    intStream.forEach(System.out::println);

    String[] stringArray = {"one", "two", "three"};
    Stream<String> stringStream = Arrays.stream(stringArray);
    stringStream.forEach(System.out::println);
  }

  private static void createFromSupplier() {
    System.out.println("> createFromSupplier");
    Stream<String> generated = Stream.generate(() -> "+");
    generated
        .limit(3) // we need this to avoid OutOfMemoryError
        .forEach(System.out::println);
  }

  private static void createFromUnaryOperator() {
    System.out.println("> createFromUnaryOperator");
    UnaryOperator<String> stringUnaryOperator = s -> s.concat("-");
    Stream<String> iterated = Stream.iterate("-", stringUnaryOperator);
    iterated
        .limit(5) // we need this to avoid OutOfMemoryError
        .forEach(System.out::println);

    // with predicate to avoid limit
    Stream.iterate("-", s -> s.length() <= 5, stringUnaryOperator)
        .forEach(System.out::println);
  }

  private static void createFromARangeOfNumbers() {
    System.out.println("> createFromARangeOfNumbers");
    String[] letters = {"A", "B", "C", "D"};
    List<String> listLetters =
        IntStream.range(0, 10)
            .peek(index -> System.out.printf("i: %d, s: %s%n", index % letters.length, letters[index % letters.length]))
            .mapToObj(index -> letters[index % letters.length])
            .toList();
    System.out.println(listLetters);
  }

  private static void createFromRandomNumbers() {
    System.out.println("> createFromRandomNumbers");
    Random random = new Random(314L); // for a given seed you will always get the same sequence of numbers
    List<Boolean> booleans = random.doubles(1000, 0d, 1d)
        .mapToObj(value -> value <= 0.8)
        .toList();

    long numberOfTrue = booleans.stream().filter(b -> b).count();
    System.out.printf("Number of true: %d%n", numberOfTrue);
  }

  private static void createFromRegularExpression() {
    System.out.println("> createFromRegularExpression");
    String sentence = "For there is good news yet to hear and fine things to be seen";
    Pattern pattern = Pattern.compile(" ");
    List<String> words = pattern.splitAsStream(sentence).toList();
    System.out.println(words);
  }
}
