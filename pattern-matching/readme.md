# Pattern matching

```java
// syntax java 16+
if (o instanceof String s){
    System.out.println("This is a String of length " + s.length());
}
```
- `String s` is a type pattern
- `s` is a pattern variable
  - this variable is created only if `o` is of type `String`
