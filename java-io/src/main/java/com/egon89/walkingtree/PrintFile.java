package com.egon89.walkingtree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


public class PrintFile extends SimpleFileVisitor<Path> {

  // print information about each type of file
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    if (attrs.isSymbolicLink()) {
      System.out.printf("Symbolic link: %s", file);
    } else if (attrs.isRegularFile()) {
      System.out.printf("Regular file: %s", file);
    } else {
      System.out.printf("Other: %s", file);
    }
    System.out.printf(" (%d bytes)%n", attrs.size());

    return FileVisitResult.CONTINUE;
  }

  // print each directory visited
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    System.out.printf("Directory: %s%n", dir);

    return FileVisitResult.CONTINUE;
  }

  // if there is some error accessing the file, print error instead throw it
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.println(exc.getMessage());

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    if (dir.getFileName().toString().contains("skip")) {
      System.out.printf("Skipping directory %s and its content%n", dir.getFileName());
      return FileVisitResult.SKIP_SUBTREE;
    }

    return FileVisitResult.CONTINUE;
  }
}
