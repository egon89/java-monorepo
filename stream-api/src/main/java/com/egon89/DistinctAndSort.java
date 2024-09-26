package com.egon89;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static java.time.Month.FEBRUARY;
import static java.time.Month.JULY;

public class DistinctAndSort {
  public static void main(String[] args) {
    var duplicateId = UUID.randomUUID();
    var list = Stream.of(
        new Person(UUID.randomUUID(), "John", LocalDate.of(1996, FEBRUARY, 10)),
        new Person(duplicateId, "Robert", LocalDate.of(1998, JULY, 20)),
        new Person(UUID.randomUUID(), "Peter", LocalDate.of(1996, FEBRUARY, 9)),
        new Person(UUID.randomUUID(), "Tomas", LocalDate.of(1996, FEBRUARY, 9)),
        new Person(duplicateId, "Robert Clone", LocalDate.of(1998, JULY, 20)))
        .distinct()
        .toList();

    System.out.println("---");
    list.forEach(System.out::println);
    /*
    hash called -> hash=4087977, birth:1996-02-10, name: John
    hash called -> hash=4092403, birth:1998-07-20, name: Robert
    hash called -> hash=4087976, birth:1996-02-09, name: Peter
    hash called -> hash=4087976, birth:1996-02-09, name: Tomas
    equals called -> the hash is equal, checking current id 5d36de53-ea7d-4681-a5c8-51a4775e65ac (Tomas) with other 84df8b77-f290-4640-b21f-318b1e55b1b4 (Peter) -> equality is false
    hash called -> hash=4092403, birth:1998-07-20, name: Robert Clone
    equals called -> the hash is equal, checking current id ff6c96ad-4b5f-4725-b9d0-e94aff4aca79 (Robert Clone) with other ff6c96ad-4b5f-4725-b9d0-e94aff4aca79 (Robert) -> equality is true
    ---
    Person{id=f1506db2-9dc1-4c75-a322-835118da303a, name=John, birth=1996-02-10
    Person{id=ff6c96ad-4b5f-4725-b9d0-e94aff4aca79, name=Robert, birth=1998-07-20
    Person{id=84df8b77-f290-4640-b21f-318b1e55b1b4, name=Peter, birth=1996-02-09
    Person{id=5d36de53-ea7d-4681-a5c8-51a4775e65ac, name=Tomas, birth=1996-02-09
     */
  }

  private static class Person {
    private final UUID id;
    private final String name;

    private final LocalDate birth;

    public Person(UUID id, String name, LocalDate birth) {
      this.id = id;
      this.name = name;
      this.birth = birth;
    }

    @Override
    public String toString() {
      return "Person{id=%s, name=%s, birth=%s".formatted(id, name, birth.toString());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Person person = (Person) o;
      var equals = Objects.equals(id, person.id);
      System.out.printf(
          "equals called -> the hash is equal, checking current id %s (%s) with other %s (%s) -> equality is %b%n",
          id, name, person.id, person.name, equals);

      return equals;
    }

    @Override
    public int hashCode() {
      var hash = Objects.hash(birth);
      System.out.printf("hash called -> hash=%d, birth:%s, name: %s%n", hash, birth.toString(), name);

      return hash;
    }
  }
}
