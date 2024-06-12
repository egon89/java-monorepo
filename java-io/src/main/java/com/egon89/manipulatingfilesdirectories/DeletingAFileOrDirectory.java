package com.egon89.manipulatingfilesdirectories;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/move-copy-delete/#deleting">dev.java</a>
 */
public class DeletingAFileOrDirectory {
  public static void main(String[] args) {
    // need to create the /tmp/file.txt for this example
    Path path = Path.of("/tmp/file.txt");

    try {
      Files.delete(path);
      System.out.format("%s deleted\n", path);
    } catch (NoSuchFileException e) {
      System.err.format("%s: no such file or directory\n", path);
    } catch (DirectoryNotEmptyException e) {
      System.err.format("%s not empty", path);
    } catch (IOException e) {
      // File permission problems are caught here
      System.err.println(e.getMessage());
    }
  }
}
