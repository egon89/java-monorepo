package com.egon89;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://dev.java/learn/records/#constructors">dev.java</a>
 */
public class DefiningAnyConstructor {
  public static void main(String[] args) {
    // creating a mutable list
    final var cities = new ArrayList<>(Arrays.asList("Canoas", "Esteio", "Sapucaia do Sul"));
    final var state = new State("Rio Grande do Sul", "Porto Alegre", cities);
    cities.add("São Leopoldo");
    System.out.println(cities); // [Canoas, Esteio, Sapucaia do Sul, São Leopoldo]
    // state cities don't change
    System.out.println(state.cities()); // [Canoas, Esteio, Sapucaia do Sul]
    System.out.println(
        state); // State[name=Rio Grande do Sul, capitalCity=Porto Alegre, cities=[Canoas, Esteio, Sapucaia do Sul]]

    final var statePR = new State("Paraná", "Curitiba");
    System.out.println(statePR); // State[name=Paraná, capitalCity=Curitiba, cities=[]]

    final var stateSC = new State("Santa Catarina", "Florianópolis", "Laguna", "Palhoça");
    System.out.println(stateSC); // State[name=Santa Catarina, capitalCity=Florianópolis, cities=[Laguna, Palhoça]]

    /*
      Compact constructor was called
      [Canoas, Esteio, Sapucaia do Sul, São Leopoldo]
      [Canoas, Esteio, Sapucaia do Sul]
      State[name=Rio Grande do Sul, capitalCity=Porto Alegre, cities=[Canoas, Esteio, Sapucaia do Sul]]
      Compact constructor was called
      Constructor with vararg cities was called
      State[name=Paraná, capitalCity=Curitiba, cities=[]]
      Compact constructor was called
      Constructor with vararg cities was called
      State[name=Santa Catarina, capitalCity=Florianópolis, cities=[Laguna, Palhoça]]
    */
  }

  private record State(String name, String capitalCity, List<String> cities) {
    public State {
      System.out.println("Compact constructor was called");
      cities = List.copyOf(cities);
    }

    public State(String name, String capitalCity, String... cities) {
      this(name, capitalCity, List.of(cities));
      System.out.println("Constructor with vararg cities was called");
    }
  }
}
