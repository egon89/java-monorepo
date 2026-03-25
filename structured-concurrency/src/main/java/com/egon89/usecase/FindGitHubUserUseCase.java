package com.egon89.usecase;

import com.egon89.model.GitHubUser;

import java.util.concurrent.ExecutionException;

public interface FindGitHubUserUseCase {
  GitHubUser findGitHubUser(long userId) throws InterruptedException, ExecutionException;
}
