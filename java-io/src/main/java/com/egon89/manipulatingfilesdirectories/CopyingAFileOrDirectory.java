package com.egon89.manipulatingfilesdirectories;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * <a href="https://dev.java/learn/java-io/file-system/move-copy-delete/#copying">dev.java</a>
 * <a href="https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#copy(java.nio.file.Path,java.nio.file.Path,java.nio.file.CopyOption...)">copy docs</a>
 * <a href="https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#copy(java.io.InputStream,java.nio.file.Path,java.nio.file.CopyOption...)">copy docs with InputStream</a>
 */
public class CopyingAFileOrDirectory {
  public static void main(String[] args) {
    Path path = Path.of("/tmp/file.txt");
    Path target = Path.of("/tmp/new-folder/copied-file.txt");
    Path pathFileInputStream = Path.of("/tmp/new-folder/copied-from-file-input-stream.txt");
    Path pathByteArrayInputStream = Path.of("/tmp/new-folder/copied-from-byte-array-input-stream.txt");

    try {
      if (Files.notExists(path)) {
        Files.createFile(path);
      }
      Files.deleteIfExists(target);
      Files.deleteIfExists(pathFileInputStream);
      Files.deleteIfExists(pathByteArrayInputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      Files.copy(path, target);
      System.out.format("File %s copied to %s\n", path, target);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    // the copy fails if the target file exists
    try {
      Files.copy(path, target);
      System.out.format("File %s copied to %s\n", path, target);
    } catch (IOException e) {
      System.err.format("Expected error when copy %s\n", e.getMessage());
    }

    // unless the replace_existing option is specified
    try {
      Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
      System.out.format("File %s copied to %s replacing the existing file\n", path, target);
    } catch (IOException e) {
      System.err.format("Error to copy %s\n", e.getMessage());
    }

    // we can use the copy_attributes to copy the file attributes associated with the original file to the target file.
    try {
      Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
      System.out.format(
          "File %s copied to %s with the original attributes and replacing the existing file\n", path, target);
    } catch (IOException e) {
      System.err.format("Error to copy %s\n", e.getMessage());
    }

    // copy using an InputStream
    try (InputStream inputStream = new FileInputStream("/tmp/file.txt")) {
      Files.copy(inputStream, pathFileInputStream);
    } catch (IOException e) {
      System.err.format("Error to copy %s with input stream\n", e.getMessage());
    }

    try (InputStream inputStream = new ByteArrayInputStream("Hello World".getBytes())) {
      Files.copy(inputStream, pathByteArrayInputStream);
    } catch (IOException e) {
      System.err.format("Error to copy %s with input stream\n", e.getMessage());
    }
  }
}
