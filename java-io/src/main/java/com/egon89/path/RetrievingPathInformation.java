package com.egon89.path;

import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/path/#getting-info">Reference</a>
 */
public class RetrievingPathInformation {
  public static void main(String[] args) {
    execute(Path.of("/tmp/foo/joe"));
    /* > output
      getFileName: joe
      getName(0): tmp
      getNameCount: 3
      subpath(0,2): tmp/foo
      getParent: /tmp/foo     -> returns de joe's parent folders
      getRoot: /
     */

    execute(Path.of("/tmp/foo/joe.txt"));
    /*
      getFileName: joe.txt
      getName(0): tmp
      getNameCount: 3
      subpath(0,2): tmp/foo
      getParent: /tmp/foo
      getRoot: /
     */
  }

  private static void execute(Path path) {
    System.out.format("getFileName: %s%n", path.getFileName());   // returns the filename or the last element
    System.out.format("getName(0): %s%n", path.getName(0));
    System.out.format("getNameCount: %d%n", path.getNameCount());
    System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
    System.out.format("getParent: %s%n", path.getParent());
    System.out.format("getRoot: %s%n", path.getRoot());
    System.out.println();
  }
}
