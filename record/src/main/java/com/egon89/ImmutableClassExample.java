package com.egon89;

public class ImmutableClassExample {
  public static void main(String[] args) {
    final var point = new Point(1, 2);
    System.out.printf("x: %d, y: %d", point.getX(), point.getY());
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
}
