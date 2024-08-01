package com.egon89.outputstream;

import java.io.*;

public class ObjectOutputStreamExample {

  private static final String FILENAME = "/tmp/person.ser";

  public static void main(String[] args) {
    serialize();
    deserialize();
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

  private static void deserialize() {
    try(FileInputStream fileInputStream = new FileInputStream(FILENAME);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
      Person person = (Person) objectInputStream.readObject();
      System.out.println("Deserialized person...");
      System.out.println(person);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.format("Person class not found: %s%n", e.getMessage());
    }
  }

  private record Person(String name, int age) implements Serializable {
      @Serial
      private static final long serialVersionUID = 1L;

    @Override
      public String toString() {
        return "Person{name='%s', age=%d}".formatted(name, age);
      }
    }
}
