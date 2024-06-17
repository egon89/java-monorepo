package com.egon89.managingfilesattributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * <a href="https://dev.java/learn/java-io/file-system/metadata/#basic">dev.java</a>
 */
public class BasicFileAttributesTutorial {
  public static void main(String[] args) throws IOException {
    Path path = Path.of("/tmp/file.txt");
    Path path2 = Path.of("/tmp/file2.txt");
    if (Files.notExists(path) || Files.notExists(path2)) throw new RuntimeException("create the files before continuing");

    BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

    System.out.println("creationTime: " + attributes.creationTime());         // creationTime: 2024-06-17T20:40:49.289889045Z
    System.out.println("lastAccessTime: " + attributes.lastAccessTime());     // lastAccessTime: 2024-06-17T20:40:49.289889045Z
    System.out.println("lastModifiedTime: " + attributes.lastModifiedTime()); // lastModifiedTime: 2024-06-17T20:40:49.289889045Z
    System.out.println("isDirectory: " + attributes.isDirectory());           // isDirectory: false
    System.out.println("isOther: " + attributes.isOther());                   // isOther: false
    System.out.println("isRegularFile: " + attributes.isRegularFile());       // isRegularFile: true
    System.out.println("isSymbolicLink: " + attributes.isSymbolicLink());     // isSymbolicLink: false
    System.out.println("size: " + attributes.size());                         // size in bytes: 7

    System.out.println(attributes.fileKey());                                 // (dev=10305,ino=11141529)

    BasicFileAttributes path2Attr = Files.readAttributes(path2, BasicFileAttributes.class);
    System.out.println(path2Attr.fileKey());                                  // (dev=10305,ino=11205364)

    // setting timestamps
    Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
    FileTime ft = Files.getLastModifiedTime(path);
    System.out.println(ft);
  }
}
