package com.egon89.usecase;

import com.egon89.model.GitHubUser;
import com.egon89.model.Repository;
import com.egon89.model.User;
import com.egon89.port.FindRepositoriesByUserIdPort;
import com.egon89.port.FindUserByIdPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.function.BiFunction;

import static com.egon89.util.Util.handleSubtaskResult;
import static com.egon89.util.Util.putIfKeyNotNull;

public class FindGitHubUserSCAwaitAllUseCase implements FindGitHubUserUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(FindGitHubUserSCAwaitAllUseCase.class);

  private final FindUserByIdPort findUserByIdPort;
  private final FindRepositoriesByUserIdPort findRepositoriesByUserIdPort;
  private final BiFunction<User, List<Repository>, GitHubUser> toGitHubUser = GitHubUser::new;

  public FindGitHubUserSCAwaitAllUseCase(
      FindUserByIdPort findUserByIdPort,
      FindRepositoriesByUserIdPort findRepositoriesByUserIdPort
  ) {
    this.findUserByIdPort = findUserByIdPort;
    this.findRepositoriesByUserIdPort = findRepositoriesByUserIdPort;
  }

  /*
    Waits for all subtasks to complete (success or failure)
    Never throws exceptions from join()
    Requires manual checking of each subtask’s state
    Useful for scenarios where partial results are acceptable
  */
  @Override
  public GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException {
    LOGGER.info("Finding GitHub user with id '{}'", userId);

    try (var scope = StructuredTaskScope.open(
        Joiner.awaitAll()
    )) {
      var userSubtask = scope.fork(() -> findUserByIdPort.findById(userId));

      var userWithErrorSubtask = scope.fork(() -> findUserByIdPort.findByIdThrowingException(userId));

      var userReposSubtask = scope.fork(() -> findRepositoriesByUserIdPort.findRepositoriesByUser(userId));

      scope.join();

      var mapResult = new HashMap<Long, GitHubUser>();

      var user = handleSubtaskResult(userSubtask, null);
      var userWithError = handleSubtaskResult(userWithErrorSubtask, null);
      var userRepos = handleSubtaskResult(userReposSubtask, List.of());

      putIfKeyNotNull(
          mapResult,
          user,
          toGitHubUser.apply(user, userRepos)
      );

      putIfKeyNotNull(
          mapResult,
          userWithError,
          toGitHubUser.apply(userWithError, List.of())
      );

      return mapResult.get(userId);
    }
  }

  /*
    [main] INFO com.egon89.Main -- Starting the application...
    [main] INFO com.egon89.usecase.FindGitHubUserSCAwaitAllUseCase -- Finding GitHub user with id '1'
    [virtual-38] INFO com.egon89.port.FindUserByIdPort -- Finding user with id '1'
    [virtual-36] INFO com.egon89.repository.GithubRepository -- Finding user with id '1' with delay 'PT0.5S'
    [virtual-40] INFO com.egon89.repository.GithubRepository -- Finding repositories for user with id '1'
    [virtual-36] INFO com.egon89.repository.GithubRepository -- User '1' found
    [virtual-40] INFO com.egon89.repository.GithubRepository -- Repositories found for user '1'
    [main] INFO com.egon89.util.Util -- Subtask completed successfully
    [main] INFO com.egon89.util.Util -- Subtask did not complete successfully. State: FAILED
    [main] INFO com.egon89.util.Util -- Subtask completed successfully
  */
}
