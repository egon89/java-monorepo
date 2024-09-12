package com.egon89;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/api/streams/intermediate-operation/#flatmap">dev.java</a>
 */
public class FlatMapExample {
  public static void main(String[] args) {
    List<State> states = stateFactory();
    System.out.printf("Population: %d%n", countPopulation(states));
    System.out.printf("Population: %d%n", countPopulationWithFlatMap(states));
  }

  private static int countPopulationWithFlatMap(List<State> states) {
    Function<State, Stream<City>> stateToCityFunction = state -> state.cities.stream();

    return states.stream()
        .flatMap(stateToCityFunction)
        .peek(System.out::println)
        .mapToInt(City::population)
        .sum();
  }

  private static int countPopulation(List<State> states) {
    int total = 0;
    for (State state: states) {
      for (City city: state.cities) {
        total += city.population;
      }
    }

    return total;
  }

  private static List<State> stateFactory() {
    return List.of(
        new State("A", List.of(new City("AA", 10))),
        new State("B", List.of(new City("BA", 20), new City("BB", 40))),
        new State("C", List.of(
            new City("CA", 60), new City("CB", 80), new City("CB", 100)))
    );
  }

  private record City(String name, int population) {}
  private record State(String name, List<City> cities) {}
}
