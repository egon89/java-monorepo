package com.egon89;

import com.egon89.repository.GithubRepository;
import com.egon89.usecase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/*
  Resources:
  - https://rockthejvm.com/articles/structured-concurrency-in-java#introduction
  - https://medium.com/@code.wizzard01/structured-concurrency-in-java-25-complete-guide-with-examples-11e049637709
*/
public class Main {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    LOGGER.info("Starting the application...");

    final var repository = new GithubRepository();

    final var useCaseList = List.of(
        new FindGitHubUserSequentialUseCase(repository, repository),
        new FindGitHubUserVirtualThreadRawUseCase(repository, repository),
        new FindGitHubUserVirtualThreadRawPreviousErrorUseCase(repository, repository),
        new FindGitHubUserVirtualThreadRawErrorUseCase(repository, repository),
        new FindGitHubUserSCUseCase(repository, repository),
        new FindGitHubUserSCPreviousErrorUseCase(repository, repository),
        new FindGitHubUserSCAllSuccessfulUseCase(repository),
        new FindGitHubUserSCAnySuccessfulUseCase(repository),
        new FindGitHubUserSCAwaitAllUseCase(repository, repository),
        new FindGitHubUserSCCustomConfigurationUseCase(repository)
    );

    for (var useCase : useCaseList) {
      try {
        useCase.findGitHubUser(1L);
      } catch (Exception e) {
        LOGGER.error("Error while finding GitHub user", e);
      }

      LOGGER.info("---");
    }
  }
}