package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class FindGitHubUserSequentialUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSequentialUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;

  public FindGitHubUserSequentialUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    var user = findUserByIdPort.findById(userId);

    var repositories = findRepositoriesByUserIdPort.findRepositoriesByUser(user.id());

    return new GitHubUser(user, repositories);
  }
}
