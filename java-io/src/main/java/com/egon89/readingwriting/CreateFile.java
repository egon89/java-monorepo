package com.egon89.readingwriting;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/small-files/#temp-files">dev.java</a>
 */
public class CreateFile {
  public static void main(String[] args) {
    createRegularFile();
    createTempFile();
  }

  private static void createRegularFile() {
    Path file = Path.of("/tmp/regular-file.txt");
    try {
      // Create the empty file with default permissions, etc.
      Files.createFile(file);
    } catch (FileAlreadyExistsException e) {
      System.err.format("file named %s already exists%n", file);
    } catch (IOException e) {
      // Some other sort of failure, such as permissions.
      System.err.format("createFile error: %s%n", e);
    }
  }

  private static void createTempFile() {
    try {
      Path tempFile = Files.createTempFile(null, ".myapp");
      System.out.format("The temporary file %s has been created%n", tempFile);
      // The temporary file has been created: /tmp/10829570997618818389.myapp
    } catch (IOException x) {
      System.err.format("IOException: %s%n", x);
    }
  }
}
