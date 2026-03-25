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

public class FindGitHubUserSCAnySuccessfulUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCAnySuccessfulUseCase.class);

  private final FindUserByIdPort findUserByIdPort;

  public FindGitHubUserSCAnySuccessfulUseCase(
      FindUserByIdPort findUserByIdPort
  ) {
    this.findUserByIdPort = findUserByIdPort;
  }

  /*
    Returns as soon as the first subtask completes successfully
    Cancels all remaining subtasks
    Returns the successful result directly from join()
    Throws exception only if all subtasks fail
  */
  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var scope = StructuredTaskScope.open(
        Joiner.<User>anySuccessfulResultOrThrow()
    )) {
      // this subtask will fail and the scope will not throw immediately
      scope.fork(() -> findUserByIdPort.findByIdThrowingException(userId));

      // this subtask will be interrupted and will not print "User '1' found" because is too long to complete
      scope.fork(() -> findUserByIdPort.findById(userId, Duration.ofSeconds(10L)));

      // this subtask will complete successfully and the scope will return immediately with the result
      scope.fork(() -> findUserByIdPort.findById(userId));

      // this subtask will be interrupted and will not print "User '1' found" because the scope will return immediately with the result of the previous subtask
      scope.fork(() -> findUserByIdPort.findById(userId, Duration.ofSeconds(5L)));

      final var user = scope.join();

      return new GitHubUser(user, List.of());
    }
  }
}
