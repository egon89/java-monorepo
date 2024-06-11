package com.egon89.manipulatingfilesdirectories;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/move-copy-delete/#checking">dev.java</a>
 */
public class CheckingAFileOrDirectory {
  public static void main(String[] args) {
    // create the /tmp/file.txt
    Path path = Path.of("/tmp/file.txt");

    System.out.println(Files.exists(path));     // true
    System.out.println(Files.notExists(path));  // false

    Path p2 = Path.of("/tmp/no-exists.txt");
    System.out.println(Files.notExists(p2));    // true
    System.out.println(!Files.exists(p2));      // true
  }
}
