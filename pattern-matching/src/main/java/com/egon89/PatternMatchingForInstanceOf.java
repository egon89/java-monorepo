package com.egon89;

/**
 * <a href="https://dev.java/learn/pattern-matching/#instanceof">dev.java</a>
 */
public class PatternMatchingForInstanceOf {
  public static void main(String[] args) {
    final var m = new MatchingAnyObjectToATypeWithInstanceOf();
    m.print("John");
    m.print(30);
  }

  private static class MatchingAnyObjectToATypeWithInstanceOf {
    public void print(Object o) {
      if (o instanceof String s) {
        System.out.printf("This is a String of length %d%n", s.length());
      } else {
        System.out.println("This is not a String");
      }
    }
  }
}
