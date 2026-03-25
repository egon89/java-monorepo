package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class FindGitHubUserSCUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;

  public FindGitHubUserSCUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  /*
    Cancels the scope if any subtask fails
    Throws the failed subtask’s exception
    Returns no result from join() - you extract results from individual subtasks
  */
  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var scope = StructuredTaskScope.open()) {
      var user = scope.fork(() -> findUserByIdPort.findById(userId));
      var repositories = scope.fork(() -> findRepositoriesByUserIdPort.findRepositoriesByUser(userId));

      // throws if either subtask fails
      scope.join();

      // both subtasks completed successfully

      return new GitHubUser(user.get(), repositories.get());

    }
  }
}
