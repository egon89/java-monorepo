package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.model.User;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

public class FindGitHubUserSCAllSuccessfulUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCAllSuccessfulUseCase.class);

  private final FindUserByIdPort findUserByIdPort;

  public FindGitHubUserSCAllSuccessfulUseCase(
      FindUserByIdPort findUserByIdPort
  ) {
    this.findUserByIdPort = findUserByIdPort;
  }

  /*
    Waits for all subtasks to complete successfully
    Cancels remaining tasks if any task fails
    Returns a stream of results from join()
    Throws exception if any task fails
  */
  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var scope = StructuredTaskScope.open(
        Joiner.<User>allSuccessfulOrThrow()
    )) {
      scope.fork(() -> findUserByIdPort.findById(userId));

      /*
        uncommenting the next fork will cause the scope to throw an exception immediately,
        without waiting for the other subtask to complete
        no "User '1' found" log will be printed in the other subtask, because the scope will be canceled immediately
      */
      // scope.fork(() -> findUserByIdPort.findByIdThrowingException(userId));

      scope.fork(() -> findUserByIdPort.findById(userId));

      // throws if either subtask fails
      scope.join()
          .map(StructuredTaskScope.Subtask::get)
          .forEach(result -> LOGGER.info("Subtask result: {}", result));


      return new GitHubUser(null, List.of());
    }
  }
}
