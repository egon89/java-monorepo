package com.egon89.createreaddirectories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * <a href="https://dev.java/learn/java-io/file-system/creating-reading-directories/#creating">dev.java</a>
 */
public class CreatingDirectory {
  public static void main(String[] args) {
    Path dir = Path.of("/tmp/folder1");
    try {
      Files.createDirectory(dir);
    } catch (IOException e) {
      System.err.println("directory already exists");
    }

    Path dir2 = Path.of("/tmp/f1/f2");
    // it will throw error because the parent folder f1 doesn't exist
    try {
      Files.createDirectory(dir2);
    } catch (IOException e) {
      System.err.format("error to create %s by createDirectory\n", dir2);
    }

    // creates a directory by creating all nonexistent parent directories first
    try {
      Files.createDirectories(dir2);
    } catch (IOException e) {
      System.err.format("error to create %s by createDirectories\n", dir2);
    }

    // create a directory with permissions
    Path dir3 = Path.of("/tmp/folder-permission");
    try {
      Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rwxr-x---");
      Files.createDirectory(dir3, PosixFilePermissions.asFileAttribute(posixFilePermissions));
    } catch (IOException e) {
      System.err.format("error to create %s\n", dir3);
    }

    // create a temporary directory
    String prefix = String.format("temp-%d", System.currentTimeMillis());
    try {
      Files.createTempDirectory(prefix);
    } catch (IOException e) {
      System.err.format("error to create %s\n", prefix);
    }
  }
}
