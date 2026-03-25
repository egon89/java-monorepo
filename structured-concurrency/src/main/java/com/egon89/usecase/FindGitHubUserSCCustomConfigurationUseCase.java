package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.model.User;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

public class FindGitHubUserSCCustomConfigurationUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCCustomConfigurationUseCase.class);

  private final FindUserByIdPort findUserByIdPort;

  public FindGitHubUserSCCustomConfigurationUseCase(
      FindUserByIdPort findUserByIdPort
  ) {
    this.findUserByIdPort = findUserByIdPort;
  }

  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    var customFactory = Thread.ofVirtual()
        .name("custom-virtual-thread", 0)
        .factory();

    try (var scope = StructuredTaskScope.open(
        Joiner.<User>allSuccessfulOrThrow(),
        cf -> cf
            .withName("user-search-scope")
            .withThreadFactory(customFactory)
            .withTimeout(Duration.ofSeconds(2)) // Set a timeout for the entire scope
    )) {
      scope.fork(() -> findUserByIdPort.findById(userId, Duration.ofSeconds(10)));

      scope.fork(() -> findUserByIdPort.findById(userId));

      // throws if either subtask fails
      scope.join()
          .map(StructuredTaskScope.Subtask::get)
          .forEach(result -> LOGGER.info("Subtask result: {}", result));


      return new GitHubUser(null, List.of());
    }
  }
}
