package com.egon89.managingfilesattributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/file-system/metadata/#mime-type">dev.java</a>
 */
public class DeterminingMimeType {
  public static void main(String[] args) throws IOException {
    Path path = Path.of("/tmp/file.txt");
    if (Files.notExists(path)) throw new RuntimeException("create the file before continuing");

    String type = Files.probeContentType(path);
    switch (type) {
      case "application/octet-stream" -> System.out.println("application/octet-stream");
      case "text/csv" -> System.out.println("text/csv");
      case "text/plain" -> System.out.println("text/plain");
      default -> System.out.println("unknown");
    }
  }
}
