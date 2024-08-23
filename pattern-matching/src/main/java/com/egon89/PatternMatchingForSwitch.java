package com.egon89;

import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/pattern-matching/#switch">dev.java</a>
 */
public class PatternMatchingForSwitch {
  public static void main(String[] args) {
    Stream.of(10, 100L, 0.9, "John").forEach(value -> {
      String formatter = switch(value) {
        case Integer i -> String.format("int %d", i);
        case Long l -> String.format("long %d", l);
        case Double d -> String.format("double %f", d);
        default -> String.format("Object %s", value);
      };

      System.out.println(formatter);
    });

    System.out.println("> Guard Patterns");
    // like if (object instanceof String s && !s.isEmpty())
    Stream.of("1", '2', "", "John").forEach(value -> {
      String formatter = switch (value) {
        case String s when !s.isEmpty() -> String.format("non-empty string %s", s);
        default -> String.format("Object %s", value);
      };
      System.out.println(formatter);
    });
  }
}
