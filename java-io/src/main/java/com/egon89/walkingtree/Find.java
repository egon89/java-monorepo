package com.egon89.walkingtree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * <a href="https://dev.java/learn/java-io/file-system/walking-tree/#finding-files">dev.java</a>
 */
public class Find {

  public static void main(String[] args) throws IOException {
    KickstartingProcess.init();

    Path target = Path.of("/tmp/f1");
    String pattern = "*.{txt,log}";
    Finder finder = new Finder(pattern);
    Files.walkFileTree(target, finder);
    finder.done();

    /*
      /tmp/f1/file.txt
      /tmp/f1/f12/file.txt
      /tmp/f1/f11/file.log
      Matched: 3
    */
  }

  public static class Finder extends SimpleFileVisitor<Path> {
    private final PathMatcher matcher;
    private int numMatches = 0;

    Finder(String pattern) {
      this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    // compares the glob pattern against the file or directory
    void find(Path path) {
      Path name = path.getFileName();
      if (Objects.nonNull(name) && matcher.matches(name)) {
        numMatches++;
        System.out.println(path);
      }
    }

    void done() {
      System.out.printf("Matched: %d%n", numMatches);
    }

    // invoke the pattern matching on each file
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      find(file);

      return FileVisitResult.CONTINUE;
    }

    // invoke the pattern matching on each directory
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      find(dir);

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
      System.err.printf("Error to access %s%n", file);
      return FileVisitResult.CONTINUE;
    }
  }
}
