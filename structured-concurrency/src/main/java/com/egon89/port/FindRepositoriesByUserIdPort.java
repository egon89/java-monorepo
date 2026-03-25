package com.egon89.port;

import com.egon89.model.Repository;

import java.util.List;

public interface FindRepositoriesByUserIdPort {
  List<Repository> findRepositoriesByUser(long userId);
}
