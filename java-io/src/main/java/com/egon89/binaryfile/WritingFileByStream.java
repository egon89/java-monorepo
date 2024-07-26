package com.egon89.binaryfile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/binary-files/#writing-bytes">dev.java</a>
 */
public class WritingFileByStream {
  public static void main(String[] args) {
    String now = DateTimeFormatter.ofPattern("hhmmss").format(LocalTime.now());
    String value = "%s Lorem ipsum.%s".formatted(now, System.lineSeparator());
    byte[] data = value.getBytes();
    Path file = Path.of("/tmp/lorem-ipsum-append.txt");

    try (OutputStream out = new BufferedOutputStream(
        Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
      out.write(data);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
