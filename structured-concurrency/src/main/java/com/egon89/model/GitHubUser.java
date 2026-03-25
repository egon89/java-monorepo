package com.egon89.model;

import java.util.List;

public record GitHubUser(User user, List<Repository> repositories) {
}
