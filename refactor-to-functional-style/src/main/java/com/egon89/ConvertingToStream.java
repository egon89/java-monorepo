package com.egon89;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/refactoring-to-functional-style/convertingtostreams/#imperativetofunctional">dev.java</a>
 */
public class ConvertingToStream {
  public static void main(String[] args) {
    final Path path = Path.of("/tmp/lorem_ipsum.txt");
    final String wordOfInterest = "ipsum";
    imperative(path, wordOfInterest);
    functionalStyle(path, wordOfInterest);
  }

  private static void imperative(Path path, String wordOfInterest) {
    try (var reader = Files.newBufferedReader(path)) {
      String line;
      long counter = 0;

      while((line = reader.readLine()) != null) {
        if(line.toLowerCase().contains(wordOfInterest.toLowerCase())) {
          counter++;
        }
      }
      System.out.printf("Word of interest counter: %d%n", counter);
    } catch (IOException e) {
      System.err.printf("Error reading %s%n", path);
    }
  }

  private static void functionalStyle(Path path, String wordOfInterest) {
    try (var reader = Files.newBufferedReader(path)) {
      long counter = reader.lines()
          .filter(line -> line.toLowerCase().contains(wordOfInterest.toLowerCase()))
          .count();
      System.out.printf("Word of interest counter: %d%n", counter);
    } catch (IOException e) {
      System.err.printf("Error reading %s%n", path);
    }
  }
}
