package com.egon89.walkingtree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/java-io/file-system/walking-tree/#copy">dev.java</a>
 */
public class Copy {

  public static void main(String[] args) throws IOException {
    KickstartingProcess.init();
    Path source = Path.of("/tmp/f1");
    Path destination = Path.of("/tmp/f1-copy");
    Replicator replicator = new Replicator(source, destination);
    Files.walkFileTree(source, replicator);
    replicator.done();
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
      System.err.printf("Unable to copy %s%n", file);

      return FileVisitResult.CONTINUE;
    }
  }
}
