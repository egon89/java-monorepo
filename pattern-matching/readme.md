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

## if vs switch statement
- Evaluating an `if-else` statement is proportional to the number of branches this statement has; doubling the number of branches, doubles the evaluation time
- The time complexity of the `if` statement is `O(n)` whereas the time complexity of the `switch` statement is `O(1)`

## Record Pattern
- A record pattern is built on the same model as the **canonical constructor** of a record
  - Even if we have other constructors in the record, the record pattern for that record always follow the syntax of the canonical constructor
