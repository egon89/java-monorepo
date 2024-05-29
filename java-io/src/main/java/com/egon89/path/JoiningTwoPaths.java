package com.egon89.path;

import java.nio.file.Path;

public class JoiningTwoPaths {
  public static void main(String[] args) {
    // > resolve()
    // we can combine paths using the resolve() method.
    // combining a root path and a partial path
    Path root = Path.of("/home/user/foo");
    Path combined = root.resolve("bar");
    System.out.println(combined); // /home/user/foo/bar
  }
}
