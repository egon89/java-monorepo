package com.egon89.createreaddirectories;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/creating-reading-directories/#listing-root">dev.java</a>
 */
public class ListingFSRootDirectories {
  public static void main(String[] args) {
    Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
    for (Path name: rootDirectories) {
      System.out.printf("%s\n", name);
    }
  }
}
