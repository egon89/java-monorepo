package com.egon89.watchingdirchanges;

import java.io.IOException;
import java.nio.file.*;

/**
 * <a href="https://dev.java/learn/java-io/file-system/watching-dir-changes/#creating-service">dev.java</a>
 */
public class WatchServiceExample {
  public static void main(String[] args) throws IOException {
    init();
    Path dir = Path.of("/tmp/f1");
    try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
      WatchKey key = dir.register(
          watcher,
          StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_DELETE,
          StandardWatchEventKinds.ENTRY_MODIFY
      );

      for (;;) {
        for (WatchEvent<?> event: key.pollEvents()) {
          WatchEvent.Kind<?> kind = event.kind();
          if (kind == StandardWatchEventKinds.OVERFLOW) continue;

          WatchEvent<Path> ev = (WatchEvent<Path>)event;
          Path filename = ev.context();

          System.out.printf("event: %s: %s%n", kind.name(), filename);
        }
        boolean valid = key.reset();
        if (!valid) break;
      }
    } catch (IOException exception) {
      System.err.println(exception.getMessage());
    }
  }

  public static void init() {
    try {
      Files.createDirectories(Path.of("/tmp/f1"));
    } catch (IOException e) {
      System.err.println("error to create directory");
    }
  }
}
