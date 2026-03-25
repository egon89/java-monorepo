package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FindGitHubUserVirtualThreadRawErrorUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserVirtualThreadRawErrorUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;

  public FindGitHubUserVirtualThreadRawErrorUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> findUserByIdPort.findById(userId));
      executor.submit(() -> findRepositoriesByUserIdPort.findRepositoriesByUser(userId));

      // even if the tasks are submitted to the executor, if we throw an exception here, the tasks will not stop and will complete their execution.
      throw new RuntimeException("Simulated error after submitting tasks to the executor");
    }
  }
}
