package com.egon89.util;

import com.egon89.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;

public final class Util {
  private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

  private Util() {
    throw new UnsupportedOperationException();
  }

  public static void delay(Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      LOGGER.error("Thread was interrupted", e);
      throw new RuntimeException("Thread was interrupted", e);
    }
  }

  public static <T> T handleSubtaskResult(StructuredTaskScope.Subtask<T> subtask, T defaultValue) {
    if (subtask.state() != StructuredTaskScope.Subtask.State.SUCCESS) {
      LOGGER.info("Subtask did not complete successfully. State: {}", subtask.state());
      return defaultValue;
    }

    LOGGER.info("Subtask completed successfully");

    return subtask.get();
  }

  public static <K, V> void putIfKeyNotNull(Map<K, V> map, Entity<K> key, V value) {
    if (key != null) {
      map.put(key.id(), value);
    }
  }
}
