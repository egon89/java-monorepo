# Record

- The Java language gives several ways to create an immutable class. The most straightforward way is to create a class with final fields and constructor to initialize these fields
- Starting with Java 14, we can use _Record_ to create immutable 'class'
```java
public record Point(int x, int y) {}
```
- it's an immutable class with two fields
- it has a _canonical constructor_, to initialize these two fields
- the `toString()`, `equals()` and `hashCode()` methods
- it can implement the `Serializable` interface, so that you can send it to other applications over a network or through a file system
- the compiler creates a final class
- a record can implement any number of interfaces
- the compiler generates one _public accessor_ for each component (x and y). This accessor is a method  that has the same name of the component returning its value
  - `public int x() { return this.x }`
  - `public int y() { return this.y }`
- we can create static fields with initializers and static initializers


### We cannot
- declare any instance field in a record that does not correspond to a component
```java
public record Person(String name, int age) {
    // This will cause a compilation error
    private String address;
}
```
- define any field initializer
```java
public record Person(String name, int age) {
  // This will cause a compilation error
  private final String name = "John Doe";
  private final int age = 30;
}
```
- add any instance initializer
```java
public record Person(String name, int age) {
    // This will cause a compilation error
    {
        System.out.println("Instance initializer");
    }
}
```

## Compact constructor
- The constructor doesn't need the parameters declaration
```java
public record Range(int start, int end) {
    public Range {
      // ... some validation here
    }
}
```
