package com.egon89.filesystem;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * <a href="https://dev.java/learn/java-io/file-system/file-system/#default">dev.java</a>
 */
public class DefaultFileSystem {
  public static void main(String[] args) {
    final var defaultFileSystem = FileSystems.getDefault();
    System.out.println(defaultFileSystem);  // sun.nio.fs.LinuxFileSystem@8efb846

    PathMatcher matcher = defaultFileSystem.getPathMatcher("glob:/tmp/*.txt");
    Path p1 = Path.of("/tmp/file.log");
    Path p2 = Path.of("/tmp/file.txt");
    Path p3 = Path.of("file.txt");

    System.out.println(matcher.matches(p1));  // false
    System.out.println(matcher.matches(p2));  // true
    System.out.println(matcher.matches(p3));  // false

    // Path String separator
    System.out.println(File.separator); // "/"
    System.out.println(defaultFileSystem.getSeparator()); // "/"

    // Files stores
    System.out.println("> Files stores");
    for (FileStore fileStore : defaultFileSystem.getFileStores()) {
      System.out.printf("name: %s - type: %s\n", fileStore.name(), fileStore.type());
    }
    /*
      name: /dev/nvme0n1p2 - type: vfat
      name: nsfs - type: nsfs
      name: sysfs - type: sysfs
      name: proc - type: proc
      name: udev - type: devtmpfs
      name: devpts - type: devpts
      name: tmpfs - type: tmpfs
      ...
     */
  }
}
