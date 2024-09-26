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
        .sorted()
        .toList();

    System.out.println("---");
    list.forEach(System.out::println);
    /*
      hash called -> hash=4087977, birth:1996-02-10, name: John
      hash called -> hash=4092403, birth:1998-07-20, name: Robert
      hash called -> hash=4087976, birth:1996-02-09, name: Peter
      hash called -> hash=4087976, birth:1996-02-09, name: Tomas
      equals called -> the hash is equal, checking current id 8e0753fb-e43d-495a-86fc-16b229a4c916 (Tomas) with other b6c80d99-bd4f-4ced-9547-cb143456a3aa (Peter) -> equality is false
      hash called -> hash=4092403, birth:1998-07-20, name: Robert Clone
      equals called -> the hash is equal, checking current id 1d463366-8dd6-4ed6-aff6-2e026142a5e3 (Robert Clone) with other 1d463366-8dd6-4ed6-aff6-2e026142a5e3 (Robert) -> equality is true
      comparing 1998-07-20 (Robert) with 1996-02-10 (John): 2
      comparing 1996-02-09 (Peter) with 1998-07-20 (Robert): -2
      comparing 1996-02-09 (Peter) with 1998-07-20 (Robert): -2
      comparing 1996-02-09 (Peter) with 1996-02-10 (John): -1
      comparing 1996-02-09 (Tomas) with 1996-02-10 (John): -1
      comparing 1996-02-09 (Tomas) with 1996-02-09 (Peter): 0
      ---
      Person{id=b6c80d99-bd4f-4ced-9547-cb143456a3aa, name=Peter, birth=1996-02-09
      Person{id=8e0753fb-e43d-495a-86fc-16b229a4c916, name=Tomas, birth=1996-02-09
      Person{id=a546388b-da0e-4d0f-ace1-6e4349c9b571, name=John, birth=1996-02-10
      Person{id=1d463366-8dd6-4ed6-aff6-2e026142a5e3, name=Robert, birth=1998-07-20
    */
  }

  private record Person(UUID id, String name, LocalDate birth) implements Comparable<Person> {

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

      @Override
      public int compareTo(Person o) {
        var compareTo = birth.compareTo(o.birth);
        System.out.printf("comparing %s (%s) with %s (%s): %d%n", birth, name, o.birth, o.name, compareTo);
        return compareTo;
      }
    }
}
