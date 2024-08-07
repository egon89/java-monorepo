package com.egon89;

/**
 * <a href="https://dev.java/learn/records/#compact">dev.java</a>
 */
public class CompactConstructor {
  public static void main(String[] args) {
    new Range(10, 5);
    try {
      new RangeValidated(10, 5);
    } catch (RuntimeException e) {
      System.err.println(e.getMessage());
    }
    new RangeAssignValue(-10, -5);
  }

  private record Range(int start, int end) {}

  private record RangeValidated(int start, int end) {
    private RangeValidated {
      if (end <= start) throw new IllegalArgumentException("End cannot be lesser than start");
    }
  }

  private record RangeAssignValue(int start, int end) {
    public RangeAssignValue {
      // we are reassigning values to parameters so that the compiler will assign to the fields
      if (start < 0) {
        System.out.println("start assigned to default value");
        start = 0;
      }
      if (end < 0) {
        System.out.println("end assigned to default value");
        end = 0;
      }

      // this.start = 0 // compile error
      // this.end = 0 // compile error
    }
  }
}
