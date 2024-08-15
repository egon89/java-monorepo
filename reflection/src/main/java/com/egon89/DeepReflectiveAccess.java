package com.egon89;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * <a href="https://dev.java/learn/introduction_to_java_reflection/#deep-reflective-access">dev.java</a>
 */
public class DeepReflectiveAccess {

  public static void main(String[] args) {
    try {
      final var person = new Person("John Doe", 30);
      System.out.println(person); // Person{John Doe, 30}
      Field ageField = Person.class.getDeclaredField("age");
      ageField.setAccessible(true); // deep reflection!
      int age = (int) ageField.get(person);
      age *= .5;
      ageField.set(person, age );
      System.out.println(person); // Person{John Doe, 15}
    } catch (NoSuchFieldException | IllegalAccessException e) {
      System.err.println(e.getMessage());
    }

    try {
      final var car = new Car("Porsche", new BigDecimal(100_000));
      Field valueField = Car.class.getDeclaredField("value");
      valueField.setAccessible(true);
      BigDecimal value = (BigDecimal) valueField.get(car);
      value = value.multiply(BigDecimal.valueOf(1.1));
      valueField.set(car, value);
      System.out.println(car);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // we cannot change record
      System.err.println(e.getMessage());
    }
  }

  public static class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "Person{%s, %d}".formatted(name, age);
    }
  }

  public record Car(String model, BigDecimal value) {}
}
