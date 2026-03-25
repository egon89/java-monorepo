package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class FindGitHubUserSCPreviousErrorUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCPreviousErrorUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;

  public FindGitHubUserSCPreviousErrorUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var scope = StructuredTaskScope.open()) {
      var user = scope.fork(() -> findUserByIdPort.findByIdThrowingException(userId));
      var repositories = scope.fork(() -> findRepositoriesByUserIdPort.findRepositoriesByUser(userId));

      // throws if either subtask fails
      scope.join();

      return new GitHubUser(user.get(), repositories.get());
    }
  }

  /*
    [main] INFO com.egon89.Main -- Starting the application...
    [main] INFO com.egon89.usecase.FindGitHubUserSCPreviousErrorUseCase -- Finding GitHub user with id '1'
    [virtual-38] INFO com.egon89.repository.GithubRepository -- Finding repositories for user with id '1'
    [virtual-36] INFO com.egon89.port.FindUserByIdPort -- Finding user with id '1'
    [virtual-38] ERROR com.egon89.Main -- Thread was interrupted <-- [IMPORTANT]
    java.lang.InterruptedException
    ...
    [main] ERROR com.egon89.Main -- Error while finding GitHub user
    java.util.concurrent.StructuredTaskScope$FailedException: java.lang.RuntimeException: User with id 1 not found

    virtual-38 started executing the task to find repositories for user with id '1',
    but virtual-36 threw an exception when trying to find the user with id '1'.
    The task in virtual-38 is interrupted because of the exception in virtual-36
     We don't see the log "Repositories found for user '1'" because the task in virtual-38 is interrupted before it can log that message.
   */
}
