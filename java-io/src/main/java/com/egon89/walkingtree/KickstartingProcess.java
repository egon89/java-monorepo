package com.egon89.walkingtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <a href="https://dev.java/learn/java-io/file-system/walking-tree/#start">dev.java</a>
 */
public class KickstartingProcess {
  public static void main(String[] args) throws IOException {
    init();
    Path startingDir = Path.of("/tmp/f1");
    PrintFile printFile = new PrintFile();
    Files.walkFileTree(startingDir, printFile);

    /* Result
      Regular file: /tmp/f1/file.txt (0 bytes)
      Skipping directory skip-this and its content
      Regular file: /tmp/f1/f12/file.txt (0 bytes)
      Regular file: /tmp/f1/f12/f121/file.html (0 bytes)
      Directory: /tmp/f1/f12/f121
      Directory: /tmp/f1/f12
      Regular file: /tmp/f1/f11/file.log (0 bytes)
      Directory: /tmp/f1/f11
      Regular file: /tmp/f1/file.html (0 bytes)
      Directory: /tmp/f1
    */
  }

  public static void init() throws IOException {
    List.of(
            Path.of("/tmp/f1"),
            Path.of("/tmp/f1/f11"),
            Path.of("/tmp/f1/f12/f121"),
            Path.of("/tmp/f1/skip-this"))
        .forEach(path -> {
          try {
            Files.createDirectories(path);
          } catch (IOException e) {
            System.err.format("error to create %s by createDirectory\n", path);
          }
        });

    Path file = Path.of("/tmp/f1/file.txt");
    if (Files.notExists(file)) {
      Files.createFile(file);
    }

    Path file2 = Path.of("/tmp/f1/file.html");
    if (Files.notExists(file2)) {
      Files.createFile(file2);
    }

    Path file3 = Path.of("/tmp/f1/f11/file.log");
    if (Files.notExists(file3)) {
      Files.createFile(file3);
    }

    Path file4 = Path.of("/tmp/f1/f12/file.txt");
    if (Files.notExists(file4)) {
      Files.createFile(file4);
    }

    Path file5 = Path.of("/tmp/f1/f12/f121/file.html");
    if (Files.notExists(file5)) {
      Files.createFile(file5);
    }

    Path file6 = Path.of("/tmp/f1/skip-this/.env");
    if (Files.notExists(file6)) {
      Files.createFile(file6);
    }
  }
}
