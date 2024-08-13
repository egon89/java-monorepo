package com.egon89;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://dev.java/learn/introduction_to_java_reflection/#the-class-class">dev.java</a>
 */
public class ClassExample {
  public static void main(String[] args) {
    // it doesn't matter the variable type, we always get the actual implementation object's class
    List<String> list1 = new ArrayList<>();
    System.out.println(list1.getClass()); // class java.util.ArrayList

    var list2 = new ArrayList<String>();
    System.out.println(list2.getClass()); // class java.util.ArrayList

    // we can use the class literals
    System.out.println(Number.class); // class java.lang.Number
    System.out.println(List.class); // interface java.util.List

    // we can also load classes by name as String
    try {
      Class<?> classLoaded = Class.forName("java.util.Iterator");
      System.out.println(classLoaded); // interface java.util.Iterator
    } catch (ClassNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }
}
