package com.egon89.binaryfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/binary-files/#reading-bytes">dev.java</a>
 */
public class ReadingFileByStream {
  public static void main(String[] args) {
    Path file = Path.of("/tmp/lorem_ipsum.txt");
    try (InputStream inputStream = Files.newInputStream(file)) {
      // The inputStream has the bytes of the file

      // Create a reader for the input stream
      // InputStreamReader is a bridge from byte streams to character streams
      // It reads bytes and decodes them into characters using a specified charset
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

      // Reads text from a character-input stream, buffering characters to provide for the efficient reading of characters, arrays, and lines
      // The Files.newBufferedReader(path) is a 'shortcut' for this whole process
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      bufferedReader.lines()
          .limit(10)
          .forEach(System.out::println);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
