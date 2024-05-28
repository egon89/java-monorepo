package com.egon89.path;

import java.nio.file.Path;

public class RemovingRedundanciesFromAPath {
  public static void main(String[] args) {
    final var redundantDirectory = "/home/./joe/foo";
    final var pd = Path.of(redundantDirectory).normalize();
    System.out.println(pd); // /home/joe/foo

    final var redundantDirectory2 = "/home/sally/../joe/foo";
    final var pd2 = Path.of(redundantDirectory2).normalize();
    System.out.println(pd2);  // /home/joe/foo
  }
}
