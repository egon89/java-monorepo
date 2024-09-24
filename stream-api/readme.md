# Stream API
- An implementation of the **map-filter-reduce** algorithm
- We can see the Stream API as a companion framework to the Collections framework
  - The Collections framework is about storing and organizing data in the memory of your JVM
- A stream is an object that **does not store** any data
- An **intermediate operation** 
  - is an operation that returns another stream
  - adds one more operation on an existing pipeline
  - doesn't process any data
- A **terminal operation** 
  - is an operation that does not return a stream
  - triggers the consumption of the elements of the stream
    - these elements are then processed by the pipeline of intermediate operations, one element at a time

## Streams of Number
- There are three specialized interfaces that use primitives types for numbers to avoid boxing and unboxing (avoiding wrapper)
  - [IntStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/stream/IntStream.html)
  - [LongStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/stream/LongStream.html)
  - [DoubleStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/stream/DoubleStream.html)

Because they are handling numbers, they have some terminal operations that don't exist in Stream:
- `sum()`: to compute the sum
- `min()`, `max()`: to compute the minimum or the maximum number of a stream
- `average()`: to compute the average value of the numbers
- `summaryStatistics()`: this call produces a special object that carries several statistics, all computed on one pass over your data. These statistics are the number of elements processed by that stream, the min, the max, the sum and the average.

## FlatMap
- This operator opens one-to-many relations between objects and create streams on these relations
- It takes a special function as an argument that returns a [Stream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/stream/Stream.html) object

### MapMulti
- the mapMulti is better than use a filter because:
  - we will need to do `Integer.parseInt` twice, the first in predicate and the second in the map
  - it's never a good idea to return values from a catch block
  ```java
    Predicate<String> isANumber = s -> {
      try {
          int i = Integer.parseInt(s);
          return true;
      } catch (NumberFormatException e) {
          return false;
      }
    };
  ``` 
- the mapMulti is better than the flatMap because:
  - if the `Integer.parseInt` goes wrong, we will need to return a `Stream.empty()`
  ```java
  Function<String, Stream<Integer>> flatParser = s -> {
      try {
          return Stream.of(Integer.parseInt(s));
      } catch (NumberFormatException e) {
      }
      return Stream.empty();
  };
  ```
- the mapMulti try to parse to int
  - if successful, a stream is returned
  - if error, the consumer is not called (no stream is created)