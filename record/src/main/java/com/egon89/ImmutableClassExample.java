package com.egon89;

public class ImmutableClassExample {
  public static void main(String[] args) {
    final var point = new Point(1, 2);
    System.out.printf("[class] x: %d, y: %d%n", point.getX(), point.getY());

    final var pointRecord = new PointRecord(1, 2);
    System.out.printf("[record] x: %d, y: %d%n", pointRecord.x(), pointRecord.y());

    new Person("John Doe", 30);
  }

  private static class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }

  private record PointRecord(int x, int y) {}

  public record Person(String name, int age) {
    // Static field with initializer
    public static final String SPECIES = "Homo sapiens";

    // Static initializer
    static {
      // cannot access instance fields here
      System.out.printf("Static initializer: %s%n", SPECIES);
    }
  }

}
