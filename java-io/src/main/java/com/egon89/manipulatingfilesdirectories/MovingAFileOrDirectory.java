package com.egon89.manipulatingfilesdirectories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * <a href="https://dev.java/learn/java-io/file-system/move-copy-delete/#moving">dev.java</a>
 */
public class MovingAFileOrDirectory {
  public static void main(String[] args) {
    Path source = Path.of("/tmp/file.txt");
    Path targetFolder = Path.of("/tmp/new-folder");
    Path target = targetFolder.resolve(Path.of("moved-file.txt"));
    init(source, targetFolder, target);

    try {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      System.err.format("%s cannot be moved: %s", source, e.getMessage());
    }

    movingEmptyDirectory();
    movingDirectoryWithContent();
  }

  private static void movingEmptyDirectory() {
    Path source = Path.of("/tmp/source-folder");
    Path targetFolder = Path.of("/tmp/new-folder");
    Path target = targetFolder.resolve(Path.of("target-folder"));

    try {
      Files.deleteIfExists(source);
      Files.deleteIfExists(target);

      Files.createDirectory(source);
      Files.move(source, target);
    } catch (IOException e) {
      System.err.format("%s cannot be moved: %s", source, e.getMessage());
    }
  }

  // see images/moving-directory-with-content.png
  private static void movingDirectoryWithContent() {
    Path content = Path.of("content.txt");
    Path source = Path.of("/tmp/source-folder");
    Path sourceContent = source.resolve(content);
    Path targetFolder = Path.of("/tmp/new-folder");
    Path target = targetFolder.resolve(Path.of("target-folder"));

    try {
      Files.deleteIfExists(source);
      Files.deleteIfExists(sourceContent);
      Files.deleteIfExists(target.resolve(content));
      Files.deleteIfExists(target);

      Files.createDirectory(source);
      Files.createFile(sourceContent);
      Files.move(source, target);
    } catch (IOException e) {
      System.err.format("cannot be moved: %s", e.getMessage());
    }
  }

  private static void init(Path source, Path targetFolder, Path target) {
    try {
      if (Files.notExists(source)) {
        Files.createFile(source);
      }
      if (Files.notExists(targetFolder)) {
        Files.createDirectory(targetFolder);
      }

      Files.deleteIfExists(target);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
