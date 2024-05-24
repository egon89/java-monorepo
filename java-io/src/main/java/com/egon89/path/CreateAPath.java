package com.egon89.path;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <a href="https://dev.java/learn/java-io/file-system/path/#create">Reference</a>
 * <a href="https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/net/URI.html">URI reference</a>
 */
public class CreateAPath {
  public static void main(String[] args) {
    // Using the Paths factory class
    Path p1 = Paths.get("/tmp/foo");
    Path p2 = Paths.get(URI.create("file:///tmp/foo"));

    // Paths.get is the same as
    Path p3 = FileSystems.getDefault().getPath("/tmp/foo");

    // Create a Path through the home directory
    Path p4 = Paths.get(System.getProperty("user.home"), "folder", "file.txt");
    System.out.println(p4); // prints /home/everton/folder/file.txt in Linux

    // Factory methods (Java 9) -> recommended
    Path p5 = Path.of("/tmp/debug.log");
    Path p6 = Path.of(URI.create("file:///tmp/foo"));
  }
}
