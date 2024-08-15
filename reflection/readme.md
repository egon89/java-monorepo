# Reflection

- Java reflection allows an object to look in the mirror and discover what fields, methods, and constructors it has.
- We can read and write fields, invoke methods, and even create new objects by calling the constructors.

## Class
- Instances of the class [Class](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/Class.html) represent classes and interfaces in a running Java application
- Each object has a `getClass()` method that it inherits from `java.lang.Object`. When we call that, we get back the actual implementation `Class`
- Once we have the class, we can find out a lot of information about it, such as:
  - who the superclasses are
  - what public members it has
  - what interfaces it has implemented
    - If it is a sealed type, we can even find the subtypes