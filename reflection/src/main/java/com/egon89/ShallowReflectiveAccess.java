package com.egon89;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * <a href="https://dev.java/learn/introduction_to_java_reflection/#shallow-reflective-access">dev.java</a>
 */
public class ShallowReflectiveAccess {
  public static void main(String[] args) {
    Stream.of(Iterator.class.getDeclaredMethods())
        .forEach(System.out::println);
    /*
      public default void java.util.Iterator.remove()
      public default void java.util.Iterator.forEachRemaining(java.util.function.Consumer)
      public abstract boolean java.util.Iterator.hasNext()
      public abstract java.lang.Object java.util.Iterator.next()
    */

    Iterator<String> i = List.of("value1", "value2", "value3").iterator();
    try {
      Method forEachRemaining = Iterator.class.getMethod("forEachRemaining", Consumer.class);
      Consumer<?> consumer = System.out::println;
      forEachRemaining.invoke(i, consumer);
      /*
        value1
        value2
        value3
      */
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
