package com.egon89.readingwriting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <a href="https://dev.java/learn/java-io/reading-writing/decorating/#gzip">dev.java</a>
 */
public class HandlingCompressedBinaryStreams {
  private static final String FILE = "/tmp/sonnet.txt.gz";

  public static void main(String[] args) {
    Path path = Path.of(FILE);
    writing(path);
    reading(path);
  }

  private static void writing(Path path) {
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
    try(var outputStream = Files.newOutputStream(path);
        var gzipOutputStream = new GZIPOutputStream(outputStream);
        var writer = new OutputStreamWriter(gzipOutputStream)) {
      writer.write(message);
      System.out.printf("> File %s compressed. Size = %d%n", path, Files.size(path));
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void reading(Path path) {
    try (var inputStream = Files.newInputStream(path);
         var gzipInputStream = new GZIPInputStream(inputStream);
         var reader = new InputStreamReader(gzipInputStream);
         var bufferedReader = new BufferedReader(reader);
         var stream = bufferedReader.lines()) {
      System.out.printf("> Reading %s file%n", path);
      String sonnet = stream.collect(Collectors.joining("\n"));
      System.out.println(sonnet);
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
