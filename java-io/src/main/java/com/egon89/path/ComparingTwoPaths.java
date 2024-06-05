package com.egon89.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/java-io/file-system/path/#comparing">dev.java</a>
 */
public class ComparingTwoPaths {
  public static void main(String[] args) throws IOException {
    Path p1 = Path.of("/tmp/debug.log");
    Path p2 = Path.of("/tmp");
    Path p3 = p2.resolve("debug.log");

    final var isEqual = p1.equals(p3);
    System.out.println(isEqual);  // true

    final var startsWith = p1.startsWith(p2);
    System.out.println(startsWith); // true

    final var endsWith = p1.endsWith(p3);
    System.out.println(endsWith); // true

    final var isEqualWithFileUtilityClass = Files.isSameFile(p1, p3);
    System.out.println(isEqualWithFileUtilityClass);  // true

    // iterate over a path
    Path p4 = Path.of("/tmp/foo/joe/sally/logs/debug.log");
    for (Path path : p4) {
      System.out.printf("p4: %s%n", path);
    }
    /*
      p4: tmp
      p4: foo
      p4: joe
      p4: sally
      p4: logs
      p4: debug.log
     */

    // compare to
    Path p5 = Path.of("/tmp/folder1/file.txt");
    Path p6 = Path.of("/tmp/folder1/folder1/file.txt");
    Path p7 = Path.of("/tmp/folder1/folder2/file.txt");
    Path p8 = Path.of("/tmp/folder2/folder1/file.txt");
    Path p9 = Path.of("/tmp/folder/folder1/file.txt");
    System.out.println(p5.compareTo(p6)); // -6
    System.out.println(p6.compareTo(p7)); // -1
    System.out.println(p6.compareTo(p8)); // -1
    System.out.println(p6.compareTo(p9)); // 2

    System.out.println("not ordered");
    Stream.of(p8, p7, p9, p5, p6).forEach(System.out::println);
    /*
      /tmp/folder2/folder1/file.txt
      /tmp/folder1/folder2/file.txt
      /tmp/folder/folder1/file.txt
      /tmp/folder1/file.txt
      /tmp/folder1/folder1/file.txt
     */

    System.out.println("ordered");
    Stream.of(p8, p7, p9, p5, p6).sorted().forEach(System.out::println);
    /*
      /tmp/folder/folder1/file.txt
      /tmp/folder1/file.txt
      /tmp/folder1/folder1/file.txt
      /tmp/folder1/folder2/file.txt
      /tmp/folder2/folder1/file.txt
     */
  }
}
