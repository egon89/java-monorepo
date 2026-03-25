package com.egon89.model;

public record User(Long id, String name) implements Entity<Long> {
}
