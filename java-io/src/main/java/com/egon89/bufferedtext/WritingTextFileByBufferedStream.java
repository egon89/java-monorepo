package com.egon89.bufferedtext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/buffered-text/#writing-text">dev.java</a>
 */
public class WritingTextFileByBufferedStream {
  public static void main(String[] args) {
    Path path = Path.of("/tmp/writing-file.txt");
    String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Á é í ó ú.";
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
      writer.write(content, 0, content.length());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    // > StandardCharsets.UTF_8 output
    // Lorem ipsum dolor sit amet, consectetur adipiscing elit. Á é í ó ú.

    // > StandardCharsets.US_ASCII output
    // Lorem ipsum dolor sit amet, consectetur adipiscing elit.
  }
}
