package com.egon89.path;

import java.io.IOException;
import java.nio.file.Path;

public class ConvertingAPath {
  public static void main(String[] args) {
    Path p1 = Path.of("/home/logfile.log");
    // if you need to convert the path to a string that can be opened from a browser
    System.out.println(p1.toUri()); // file:///home/logfile.log

    // the toAbsolutePath() method can be very helpful when processing user-entered file names.
    // you can ensure that your code works consistently regardless of the starting point of the path.
    // the file doesn't need to exist
    Path p2 = Path.of("temp/file.txt");
    Path p2f = p2.toAbsolutePath();
    System.out.println(p2f);  // /home/user/temp/file.txt

    // the toRealPath() method returns the real path of an existing file.
    // Example 1: Resolving a relative path
    Path relativePath = Path.of("temp/existing-file.txt");
    try {
      Path realPath = relativePath.toRealPath();
      System.out.println("Real path: " + realPath);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
