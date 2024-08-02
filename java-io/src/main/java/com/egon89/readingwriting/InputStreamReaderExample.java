package com.egon89.readingwriting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/decorating/#stream-reader">dev.java</a>
 */
public class InputStreamReaderExample {
  public static void main(String[] args) {
    Path path = Path.of("/tmp/sonnet.txt");
    if (Files.notExists(path)) throw new RuntimeException("create file through OutputStreamWriterExample");

    // the same as Files.newBufferedReader(path) :)
    try (var inputStream = Files.newInputStream(path);
      var reader = new InputStreamReader(inputStream);
      var bufferedReader = new BufferedReader(reader);
      Stream<String> lines = bufferedReader.lines()
      ) {
      String sonnet = lines.collect(Collectors.joining("\n"));
      System.out.println(sonnet);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
