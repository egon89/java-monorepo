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

---
**Links**
- [Java I/O intro](https://dev.java/learn/java-io/intro/)