package com.egon89.listing;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <a href="https://dev.java/learn/java-io/file-system/listing/#globbing">dev.java</a>
 */
public class FilteringDirectoryListingByUsingGlobbing {
  public static void main(String[] args) throws IOException {
    init();
    Path path = Path.of("/tmp/f1");

    System.out.println("> Filter by txt and log files");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{txt,log}")) {
      stream.forEach(entry -> System.out.println(entry.getFileName()));
    } catch (IOException exception) {
      System.err.format("error listing %s", path);
    }

    System.out.println("> Filter by directories");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, new DirectoryFilter())) {
      stream.forEach(entry -> System.out.println(entry.getFileName()));
    } catch (IOException exception) {
      System.err.format("error listing %s", path);
    }

    System.out.println("> Filter by hidden files");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, Files::isHidden)) {
      stream.forEach(entry -> System.out.println(entry.getFileName()));
    } catch (IOException exception) {
      System.err.format("error listing %s", path);
    }

    System.out.println("> Filter by readable files");
    final DirectoryStream.Filter<Path> readableFile = Files::isReadable;
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, readableFile)) {
      stream.forEach(entry -> System.out.println(entry.getFileName()));
    } catch (IOException exception) {
      System.err.format("error listing %s", path);
    }

    /*
      > Filter by txt and log files
      file.txt
      file.log
      > Filter by directories
      f12
      f11
      > Filter by hidden files
      .env
      > Filter by readable files
      file.txt
      file.log
      .env
      f12
      f11
      file.html
    */
  }

  private static void init() throws IOException {
    List.of(
            Path.of("/tmp/f1"),
            Path.of("/tmp/f1/f11"),
            Path.of("/tmp/f1/f12/f121"))
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

    Path file3 = Path.of("/tmp/f1/file.log");
    if (Files.notExists(file3)) {
      Files.createFile(file3);
    }

    Path file4 = Path.of("/tmp/f1/.env");
    if (Files.notExists(file4)) {
      Files.createFile(file4);
    }
  }

  private static class DirectoryFilter implements DirectoryStream.Filter<Path> {
    @Override
    public boolean accept(Path entry) {
      return Files.isDirectory(entry);
    }
  }
}
