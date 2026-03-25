package com.egon89.port;

import com.egon89.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.egon89.util.Util.delay;

public interface FindUserByIdPort {
  Logger LOGGER = LoggerFactory.getLogger(FindUserByIdPort.class);

  User findById(long id);

  User findById(long id, Duration delay);

  default User findByIdThrowingException(long id) {
    LOGGER.info("Finding user with id '{}'", id);

    delay(Duration.ofMillis(100L));

    throw new RuntimeException("User with id %s not found".formatted(id));
  }
}
