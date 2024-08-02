package com.egon89.readingwriting;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/decorating/#stream-reader">dev.java</a>
 */
public class OutputStreamWriterExample {
  public static void main(String[] args) {
    String message = """
        From fairest creatures we desire increase,
        That thereby beauty's rose might never die,
        But as the riper should by time decease
        His tender heir might bear his memory:
        But thou, contracted to thine own bright eyes,
        Feed'st thy light's flame with self-substantial fuel,
        Making a famine where abundance lies,
        Thyself thy foe, to thy sweet self too cruel.
        Thou that art now the world's fresh ornament,
        And only herald to the gaudy spring,
        Within thine own bud buriest thy content,
        And, tender churl, mak'st waste in niggardly.
        Pity the world, or else this glutton be,
        To eat the world's due, by the grave and thee.""";

    Path path = Path.of("/tmp/sonnet.txt");
    try (var outputStream = Files.newOutputStream(path);
         var writer = new OutputStreamWriter(outputStream)) {
      writer.write(message);
      long size = Files.size(path);
      System.out.println("size = " + size);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
