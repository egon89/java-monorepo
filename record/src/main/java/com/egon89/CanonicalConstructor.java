package com.egon89;

/**
 * <a href="https://dev.java/learn/records/#canonical">dev.java</a>
 */
public class CanonicalConstructor {
  public static void main(String[] args) {
    System.out.println(new Range(-10, -1)); // Range[start=0, end=1]
    System.out.println(new Range(0, 10)); // Range[start=0, end=10]
    System.out.println(new Range(10, -1)); // throws IllegalArgumentException
  }

  private record Range(int start, int end) {
    private Range(int start, int end) {
      if (end <= start) throw new IllegalArgumentException("End cannot be lesser than start");
      this.start = Math.max(start, 0);
      this.end = Math.max(end, 1);
    }
  }
}
