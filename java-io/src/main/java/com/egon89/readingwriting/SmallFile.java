package com.egon89.readingwriting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/small-files/#small-files">dev.java</a>
 * <a href="https://mkyong.com/java/how-do-convert-byte-array-to-string-in-java/">mkyong</a>
 */
public class SmallFile {
  public static void main(String[] args) throws IOException {
    readingAllBytesFromAPlainTextFile();
    readingAllLinesFromAPlainTextFile();
    readingAllBytesFromABinaryData();
  }

  private static void readingAllBytesFromAPlainTextFile() throws IOException {
    Path path = Path.of("/tmp/small-content.txt");
    byte[] bytes = Files.readAllBytes(path);
    String content = new String(bytes);
    System.out.println(content.substring(0, 200));
  }

  private static void readingAllLinesFromAPlainTextFile() throws IOException {
    Path path = Path.of("/tmp/small-content.txt");
    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
    lines.stream().limit(10).forEach(System.out::println);
  }

  private static void readingAllBytesFromABinaryData() throws IOException {
    Path path = Path.of("/tmp/small-image.png");
    byte[] bytes = Files.readAllBytes(path);
    String binaryContent = Base64.getEncoder().encodeToString(bytes);
    System.out.println(binaryContent);

    writeBinaryContent(binaryContent, Path.of("/tmp/small-image-copy.png"));
  }

  private static void writeBinaryContent(String binaryContent, Path path) throws IOException {
    byte[] decode = Base64.getDecoder().decode(binaryContent);
    Files.write(path, decode);
  }
}
