package com.egon89.repository;

import com.egon89.model.Repository;
import com.egon89.model.User;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import static com.egon89.util.Util.delay;

public class GithubRepository implements FindUserByIdPort, FindRepositoriesByUserIdPort {
  private static final Logger LOGGER = LoggerFactory.getLogger(GithubRepository.class);

  @Override
  public List<Repository> findRepositoriesByUser(long userId) {
    LOGGER.info("Finding repositories for user with id '{}'", userId);

    delay(Duration.ofSeconds(1L));

    LOGGER.info("Repositories found for user '{}'", userId);

    return List.of(
        new Repository(
            "repo1", URI.create("https://github.com/johndoe/repo1")),
        new Repository(
            "repo2", URI.create("https://github.com/johndoe/repo2")));
  }

  @Override
  public User findById(long id) {
    return findById(id, Duration.ofMillis(500L));
  }

  @Override
  public User findById(long id, Duration delay) {
    LOGGER.info("Finding user with id '{}' with delay '{}'", id, delay);

    delay(delay);

    LOGGER.info("User '{}' found", id);

    return new User(id, "John Doe");
  }
}
