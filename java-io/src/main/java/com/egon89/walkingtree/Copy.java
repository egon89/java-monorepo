package com.egon89.walkingtree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/java-io/file-system/walking-tree/#copy">dev.java</a>
 */
public class Copy {

  public static void main(String[] args) throws IOException {
    init();
    Path source = Path.of("/tmp/f1");
    Path destination = Path.of("/tmp/f1-copy");
    Replicator replicator = new Replicator(source, destination);
    Files.walkFileTree(source, replicator);
    replicator.done();
    /*
      Copy directory: /tmp/f1
      Copy directory: /tmp/f1/f12
      Copy directory: /tmp/f1/f12/f121
      Copying file: /tmp/f1/f12/f121/e.html
      Copying file: /tmp/f1/f12/d.txt
      Copy directory: /tmp/f1/f13
      Copying file: /tmp/f1/f13/.env
      Copy directory: /tmp/f1/f11
      Copying file: /tmp/f1/f11/c.log
      Copying file: /tmp/f1/b.html
      Copying file: /tmp/f1/a.txt
      Number of files copied: 2
    */
  }

  public static void init() throws IOException {
    List.of(
            Path.of("/tmp/f1"),
            Path.of("/tmp/f1/f11"),
            Path.of("/tmp/f1/f12/f121"),
            Path.of("/tmp/f1/f13"))
        .forEach(path -> {
          try {
            Files.createDirectories(path);
          } catch (IOException e) {
            System.err.format("error to create %s by createDirectory\n", path);
          }
        });

    Path file = Path.of("/tmp/f1/a.txt");
    if (Files.notExists(file)) {
      Files.createFile(file);
    }

    Path file2 = Path.of("/tmp/f1/b.html");
    if (Files.notExists(file2)) {
      Files.createFile(file2);
    }

    Path file3 = Path.of("/tmp/f1/f11/c.log");
    if (Files.notExists(file3)) {
      Files.createFile(file3);
    }

    Path file4 = Path.of("/tmp/f1/f12/d.txt");
    if (Files.notExists(file4)) {
      Files.createFile(file4);
    }

    Path file5 = Path.of("/tmp/f1/f12/f121/e.html");
    if (Files.notExists(file5)) {
      Files.createFile(file5);
    }

    Path file6 = Path.of("/tmp/f1/f13/.env");
    if (Files.notExists(file6)) {
      Files.createFile(file6);
    }
  }

  public static class Replicator extends SimpleFileVisitor<Path> {
    final Path source;
    final Path destination;

    public Replicator(Path source, Path destination) {
      this.source = source;
      this.destination = destination;
    }

    void done() throws IOException {
      try (Stream<Path> streamPath = Files.list(Path.of(destination.toUri()))) {
        System.out.printf("Number of files copied: %d%n",
            streamPath.filter(path -> path.toFile().isFile()).count());
      }
    }

    /*
      Example for file /tmp/f1/file.txt
      source: /tmp/f1
      destination: /tmp/f1-copy
      relativize: file.txt
      new file: /tmp/f1-copy/file.txt
      */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      System.out.printf("Copying file: %s%n", file);
      Path relativize = source.relativize(file);
      Path newFile = destination.resolve(relativize);

      try {
        Files.copy(file, newFile);
      } catch (IOException exception) {
        System.err.printf("Error copying file %s%n", file);
      }

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      System.out.printf("Copy directory: %s%n", dir);
      Path relativize = source.relativize(dir);
      Path targetDirectory = destination.resolve(relativize);
      try {
        Files.copy(dir, targetDirectory, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
      } catch (IOException exception) {
        System.err.printf("Unable to create directory %s [%s]%n", targetDirectory, exception.getMessage());

        return FileVisitResult.SKIP_SUBTREE;
      }

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      if (Objects.nonNull(exc)) throw exc;

      Path target = destination.resolve(source.relativize(dir));
      try {
        FileTime lastModifiedTime = Files.getLastModifiedTime(dir);
        Files.setLastModifiedTime(target, lastModifiedTime);
      } catch (IOException exception) {
        System.err.printf("Unable to copy all attributes to %s [%s]%n", target, exception.getMessage());
      }

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
      System.err.printf("Unable to copy: %s%n", file);

      return FileVisitResult.CONTINUE;
    }
  }
}
