package com.egon89.outputstream;

import java.io.*;

public class ObjectOutputStreamExample {

  private static final String FILENAME = "/tmp/person.ser";

  public static void main(String[] args) {
    serialize();
  }

  private static void serialize() {
    Person person = new Person("John Doe", 30);
    try(FileOutputStream fileOutputStream = new FileOutputStream(FILENAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
    ) {
      objectOutputStream.writeObject(person);
      System.out.println("Serialized data is saved");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private static class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "Person{name='%s', age=%d}".formatted(name, age);
    }
  }
}
