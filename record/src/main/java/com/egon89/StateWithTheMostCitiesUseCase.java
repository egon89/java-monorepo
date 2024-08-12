package com.egon89;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateWithTheMostCitiesUseCase {
  public static void main(String[] args) {
    List<City> cities = List.of(
        new City("City 1", new State("S1")),
        new City("City 2", new State("S2")),
        new City("City 3", new State("S2")),
        new City("City 4", new State("S1")),
        new City("City 5", new State("S1")),
        new City("City 6", new State("S3"))
    );

    Map<State, Long> numberOfCitiesPerState =
        cities.stream()
            .collect(Collectors.groupingBy(City::state, Collectors.counting()));

    System.out.println(numberOfCitiesPerState);

    NumberOfCitiesPerState stateWithTheMostCities = numberOfCitiesPerState.entrySet().stream()
        .map(NumberOfCitiesPerState::new)
        .max(NumberOfCitiesPerState.comparingByNumberOfCities())
        .orElseThrow();

    System.out.println(stateWithTheMostCities);
  }

  private record City(String name, State state) {}

  private record State(String name) {}

  private record NumberOfCitiesPerState(State state, long numberOfCities) {
    public NumberOfCitiesPerState(Map.Entry<State, Long> entry) {
      this(entry.getKey(), entry.getValue());
    }

    public static Comparator<NumberOfCitiesPerState> comparingByNumberOfCities() {
      return Comparator.comparing(NumberOfCitiesPerState::numberOfCities);
    }
  }
}
