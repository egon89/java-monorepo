package com.egon89.path;

import java.io.IOException;
import java.nio.file.Path;

public class ConvertingAPath {
  public static void main(String[] args) {
    Path p1 = Path.of("/home/logfile.log");
    // if you need to convert the path to a string that can be opened from a browser
    System.out.println(p1.toUri()); // file:///home/logfile.log

    // > toAbsolutePath()
    // the toAbsolutePath() method can be very helpful when processing user-entered file names.
    // you can ensure that your code works consistently regardless of the starting point of the path.
    // the file doesn't need to exist
    Path p2 = Path.of("/tmp/file.txt");
    Path p2f = p2.toAbsolutePath();
    System.out.println(p2f);  // /tmp/file.txt

    // pay attention to the first slash
    Path fromCurrent = Path.of("logs/debug.log").toAbsolutePath();
    Path fromRoot = Path.of("/logs/debug.log").toAbsolutePath();
    System.out.println(fromCurrent); // /home/user/projects/workspace/logs/debug.log
    System.out.println(fromRoot); // /logs/debug.log

    // > toRealPath()
    // the toRealPath() method returns the real path of an existing file.
    // Example 1: Resolving a relative path
    Path relativePath = Path.of("/tmp/existing-file.txt"); // run touch /tmp/existing-file.txt first
    try {
      Path realPath = relativePath.toRealPath();
      System.out.println("Real path: " + realPath); // Real path: /tmp/existing-file.txt
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Example 2: Resolving a relative path
    Path relativePath2 = Path.of("/tmp/no-existing-file.txt");
    try {
      Path realPath2 = relativePath2.toRealPath();
      System.out.println("Real path: " + realPath2);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage()); // Error: /tmp/no-existing-file.txt
    }
  }
}
