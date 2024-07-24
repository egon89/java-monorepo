package com.egon89.bufferedtext;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/buffered-text/#reading-text">dev.java</a>
 */
public class ReadingTextFileByBufferedStream {
  public static void main(String[] args) {
    Path path = Path.of("/tmp/lorem_ipsum.txt");
    try(
        BufferedReader reader = Files.newBufferedReader(path);
        Stream<String> lines = reader.lines()
    ) {
      long count = lines
          .peek(System.out::println)
          .count();
      System.out.printf("lines: %d%n", count);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
