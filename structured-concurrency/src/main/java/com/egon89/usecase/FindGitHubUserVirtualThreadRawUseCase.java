package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FindGitHubUserVirtualThreadRawUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserVirtualThreadRawUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;

  public FindGitHubUserVirtualThreadRawUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      var user = executor.submit(() -> findUserByIdPort.findById(userId));
      var repositories = executor.submit(() -> findRepositoriesByUserIdPort.findRepositoriesByUser(userId));

      return new GitHubUser(user.get(), repositories.get());
    }
  }
}
