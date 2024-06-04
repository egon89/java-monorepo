package com.egon89.path;

import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/path/#relativizing">dev.java</a>
 * <a href="https://jenkov.com/tutorials/java-nio/path.html#relativize()">jenkov.com</a>
 */
public class CreatingAPathBetweenTwoPaths {
  public static void main(String[] args) {
    Path p1 = Path.of("/home");
    Path p2 = Path.of("/home/user/logs/debug.log");

    Path p3 = p1.relativize(p2);
    System.out.println(p3); // user/logs/debug.log

    Path p4 = p2.relativize(p1);
    System.out.println(p4); // ../../.. -> we need to navigate three levels to reach the same node (/home)

    //
    Path joePath = Path.of("joe");
    Path sallyPath = Path.of("sally");

    System.out.println(joePath.relativize(sallyPath));  // ../sally
    System.out.println(sallyPath.relativize(joePath));  // ../joe

    // we cannot mix relative and absolute paths when trying to calculate the relative path
    Path base = Path.of("/tmp");
    Path path = Path.of("my-file.txt");
    try {
      Path pathError = base.relativize(path);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }
}
