package com.egon89.listing;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <a href="https://dev.java/learn/java-io/file-system/listing/#listing-content">dev.java</a>
 */
public class ListingContentOfADirectory {
  public static void main(String[] args) throws IOException {
    init();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of("/tmp/f1"))) {
      for (Path path : stream) {
        System.out.println(path);
      }
    } catch (IOException | DirectoryIteratorException exception) {
      System.err.println(exception.getMessage());
    }
    /*
      /tmp/f1/file.txt
      /tmp/f1/f12
      /tmp/f1/f11
   */
  }

  private static void init() throws IOException {
    List.of(
        Path.of("/tmp/f1"),
        Path.of("/tmp/f1/f11"),
        Path.of("/tmp/f1/f12/f121"))
    .forEach(path -> {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        System.err.format("error to create %s by createDirectory\n", path);
      }
    });

    Path file = Path.of("/tmp/f1/file.txt");
    if (Files.notExists(file)) {
      Files.createFile(file);
    }

    Path file2 = Path.of("/tmp/f1/f11/file-11.txt");
    if (Files.notExists(file2)) {
      Files.createFile(file2);
    }
  }
}
