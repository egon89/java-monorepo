# Java I/O
![horizontal_rule](../resources/horizontal_rule.png)

- The Java I/O gives all the tools your application needs to access information from the outside
  - file system
  - memory
  - network
- The APIs that give you access to a database (the Java Database Connectivity API) use the Java I/O API to access databases through TCP/IP
- Versions
  - Java I/O (first versions of the JDK)
  - Java NIO (1.4)
  - Java NIO2 (1.7+)
- There are two ways to access files in the Java I/O API
    - File class
    - Path interface

## File class
- It works as a wrapper on a string of characters representing a path on your file system
- The path can be absolute or relative
- It can be represented a regular **file** or a **directory**
- What can I do?
    - check if a file exists
    - if we can read or modify a file
    - create, copy or check for its content

### Problems
- The File class doesn't throw exceptions when they fail
  - if an error occurs in the file.delete(), we don't know if the error is about because the file does not exist, or we don't have permission, etc.
- Requesting a large directory listing over a server can result in a hang. Large directories can also cause memory resource problems, resulting in a denial of service.

All these issues are fixed by the **Path interface**. So using the **File class is not recommended anymore**

## Path
- A Path instance contains the information used to specify the location of a **file** or **directory**.
- You can think of the Path as storing elements as a sequence, where:
  - the highest element in the directory structure would be located at index 0
  - the lowest element in the directory structure would be located at index [n-1]
  - the Path interface extends the [Iterable](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/Iterable.html) interface, so we can **iterate** over the elements in the path
  - the Path interface extends the [Comparable](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/Comparable.html) interface, so we can compare Path objects by using `compareTo()`, which is useful for **sorting**
- What is a [URI](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/net/URI.html)?
  - represents a Uniform Resource Identifier (URI) reference
  - in string form has the syntax `[scheme:]scheme-specific-part[#fragment]`
    ``` 
        mailto:java-net@www.example.com
        news:comp.lang.java
        urn:isbn:096139210x
        http://example.com/languages/java/
        file:///~/calendar 
      ```

### Normalize
- Returns a path that is this path with redundant name elements eliminated.
  - In such file systems all occurrences of `.` are considered redundant.
  - If a `..` is preceded by a `non-".."` name then both names are considered redundant
  - [Documentation](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Path.html#normalize())

### Relativize
The Java Path method `relativize()` can create a new Path which represents the second Path relative to the first Path.

For instance:
- The first path is `/data`
- The second path is `/data/subdata/subsubdata/myfile.txt`

The second path can be expressed as `/subdata/subsubdata/myfile.txt` relative to the first path.

## File System
### Files Stores
- A file system has one or more file stores to hold its files and directories
- The file store represents the underlying storage device
  - In UNIX operating systems, each mounted file system is represented by a file store
  - In Microsoft Windows, each volume is represented by a file store

## Manipulating files and directories
- The `deleteIfExists` method: 
  - It doesn't throw the `NoSuchFileException` when a file doesn't exist
    - It's useful when we have multiple threads deleting files, and you don't want to throw an exception just because one thread did so first
  - But, it throws the `DirectoryNotEmptyException` and `IOException` exceptions

- The `copy` method:
  - Directories can be copied. However, **files inside the directory are not copied**. So the new directory is empty even when the original directory contains files

## Moving files and directories
- Empty directories can be moved
- On UNIX, moving a directory within the same level consists of renaming the directory (even when it contains files)

## Atomic operations
- It's an operation that cannot be interrupted or "partially" performed. Either the entire operation is performed or the operation fails
- Several [Files](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html) methods can perform operations atomically

## I/O Stream
- A stream is a sequence of data
    - A program uses an input stream to read data from a source, one item at a time and an output stream to write data to a destination, one item at time
        - source and destination can be disk files, devices, other programs, memory arrays, network sockets, etc.
- The Java I/O API defines two kinds of content for a resource:
    - Character content
        - Like a text file (JSON, XML document)
        - All character stream classes are descended from **Reader** and **Writer**
    - Byte content
        - Like an image or a video
        - All byte streams classes are descended from **InputStream** and **OutputStream**

## Input Stream
- It's an abstract class that represents an input stream of bytes
- It's used **to read data from a source**, such as a file or network connection
- To create an `InputStream`, we can use various subclasses, such as:
  - `FileInputStream`
  - `ByteArrayInputStream`
  - `BufferedInputStream`

```java
// using FileInputStream
InputStream input = new FileInputStream("file.txt");

// using ByteArrayInputStream
InputStream input = new ByteArrayInputStream("Hello World!".getBytes());

// using BufferedInputStream
InputStream input = new BufferedInputStream(new FileInputStream("file.txt"));

// using InputStreamReader
InputStream input = new FileInputStream("file.txt");
InputStreamReader reader = new InputStreamReader(input);

// using try-with-resources
try (InputStream input = new FileInputStream("file.txt")) {
  // Use the input stream
}
```

---
**Links**
- [Java I/O intro](https://dev.java/learn/java-io/intro/)