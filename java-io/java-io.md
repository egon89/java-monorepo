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

## File and File Store Attributes
- The [Files](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html) class includes methods that can be used to obtain a single attribute of a file
- We can use the `Files.readAttributes()` to get file's attributes in one bulk operation 

Different file systems have different notions about which attributes should be tracked. For this reason, related file attributes are grouped together into views:
- [BasicFileAttributeView](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/attribute/BasicFileAttributeView.html) interface
  - A file attribute view that provides a view of a basic set of file attributes common to many file systems
  - Contains the _readAttributes()_ method that reads the basic file attributes as a bulk operation returning an `BasicFileAttributes`
- [BasicFileAttributes](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/attribute/BasicFileAttributes.html) class
  - Basic attributes associated with a file in a file system 
  - Basic file attributes are attributes that are common to many file systems
- [FileOwnerAttributeView](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/attribute/FileOwnerAttributeView.html) interface
  - It supports reading or updating the owner of a file
- [UserDefinedFileAttributeView](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/attribute/UserDefinedFileAttributeView.html) interface
  - It provides a view of a file's user-defined attributes, sometimes known as extended attributes (metadata defined by user)
    - You can use this view to store the MIME type of file

### BasicFileAttributes
- The [_fileKey()_](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/attribute/BasicFileAttributes.html#fileKey()) method returns an object that **uniquely identifies the given file**, or null if a file key is not available
```java
BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
System.out.println(attr.fileKey());  // (dev=10305,ino=11205364)
```

### POSIX File Permissions
- POSIX (Portable Operating System Interface for Unix) is a set of standards designed to ensure interoperability among different flavors of UNIX
- Besides file owner and group owner, POSIX supports file permissions like:
  - read, write and execute permissions 
    - for the 
      - file owner,
      - members of the same group
      - everyone else

### Setting a file or group owner
- We can use `cat /etc/passwd` to list users on Linux
- We can use `cat /etc/group` to list groups on Linux

## Listing
### Listing content of a directory
- [DirectoryStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/DirectoryStream.html)
  -  The Path objects returned by the iterator (DirectoryStream) are the names of the entries **resolved** against the directory
      - if we are listing the contents of the `/tmp` directory, the entries are returned with the form `/tmp/a`, `/tmp/b` and so on
  - This method returns the entire content of a directory
    - files
    - links
    - subdirectories (non-recursively)
    - hidden files
  - We can provide a [glob filter]((https://dev.java/learn/java-io/file-system/listing/#glob)) to only get files and subdirectories that follow a pattern

## Walking the File Tree
Do you need to create an application that will recursively visit all the files in a file tree?
Perhaps you need to delete every _.class_ file in a tree, or find every file that has not been accessed in the last year.
You can do so with the [FileVisitor](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/FileVisitor.html) interface.
- FileVisitor interface methods
  - `preVisitDirectory()`
  - `postVisitDirectory()`
  - `visitFile()`
  - `visitFileFailed()`

> [Considerations](https://dev.java/learn/java-io/file-system/walking-tree/#considerations) when creating a FileVisitor

If we don't need to implement all four of the **FileVisitor** methods, we can extend the [**SimpleFileVisitor**](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/SimpleFileVisitor.html) class, an adapter class, and override only the desired methods.

## Watching a directory changes
- To implement a file change notification, we can use the notification API [WatchService](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/WatchService.html)
- We can register a directory (or directories) and tell which types of events we are interested in
  - file creation
  - file deletion
  - file modification
- Each register has a thread (or a pool of threads) dedicated to watching for any events it has registered for
  - any object that implements the [Watchable](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Watchable.html) interface can be registered
  - the [Path](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Path.html) implements `Watchable`, so each directory to be monitored can be registered as a `Path` object

> We can use the Watch Service API to watches a directory, perhaps waiting for .jar files to drop, in order to deploy them

<img src="https://raw.githubusercontent.com/egon89/resources/main/gifs/watchservice.gif" width="80%" />

# File Operations Basics
- Small files: we can load in memory
- Large files: we access through buffers

## Commonly used methods for small files
- We can use the [readAllBytes](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#readAllBytes(java.nio.file.Path)) or [readAllLines](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#readAllLines(java.nio.file.Path,java.nio.charset.Charset)) methods to read an entire file content
- We can use the [write](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#write(java.nio.file.Path,byte%5B%5D,java.nio.file.OpenOption...)) method to writes bytes to a file

## Reading a Text File by Using Buffered Stream I/O
- Input and output done with stream classes **automatically translates** to and from the local character set (UTF-8/ASCII)
- The [newBufferedReader](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#newBufferedReader(java.nio.file.Path,java.nio.charset.Charset)) method opens a file for reading, returning a `BufferedReader` that can be used to read text from a file in an efficient manner
  - The `BufferedReader` class gives you a method to read the content of your text file line by line

---

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

## [BufferedReader](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/io/BufferedReader.html)
- Reads text from a character-input stream, buffering characters to provide for the efficient reading of characters, arrays, and lines

## [OutputStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/io/OutputStream.html)
- It's a superclass of all classes representing an output stream
- An output stream accepts output bytes and sends them to some sink

### [Files.newOutputStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/nio/file/Files.html#newOutputStream(java.nio.file.Path,java.nio.file.OpenOption...))
- Opens or create a file, returning an output stream that may be used to write bytes to the file

## [BufferedOutputStream](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/io/BufferedOutputStream.html)
- It provides buffering for output streams (wrapping an existing OutputStream) reducing the number of write operations
- It achieves this by maintaining an internal buffer and only writing data to the underlying stream when the buffer is full or when the stream is explicitly flushed
- The default buffer size is 8192 bytes (8 KB)

Scenario: Writing a 32 KB file using 16 KB for buffer
- The `write(byte[] b)` will receive the entire 32 KB of data
- The `BufferedOutputStream` writes the first 16 KB (first write call)
- The buffer is full, it flushes the 16 KB buffer to the underlying `FileOutputStream`, which writes it to the file
- The remaining 16 KB of data is written to the buffer
- Once again, when the buffer is full, it is flushed to the underlying `FileOutputStream` (second write call)

> We call the `write(byte[] b)` only one time. The `BufferedOutputStream` internally handles the process of breaking the data into chunks that fit the buffer size and flushing them as needed

## [ByteArrayOutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayOutputStream.html)
- It extends `OutputStream` and provides a way to write data to a byte array
- The buffer automatically grows as data is written to it
- When to use it:
  - Collection data in memory: when you need to write data to a buffer that can grow as needed (we don't know how much data will be written)
  - Data manipulation: if we need to manipulate or inspect data (keeping data in memory) before writing it to its final destination 

## Writing and reading characters to binary streams
- Java I/O API is divided into two categories, one to handle characters and the other to handle bytes
- Writing characters to binary files is something that is widely used in applications
- The Java I/O API gives two classes for that:
  - [InputStreamReader](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/io/InputStreamReader.html) that can read characters from an `InputStream`
    - `InputStreamReader` is a decoration of the `Reader` class, built on an `InputStream`
  - [OutputStreamWriter](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/io/OutputStreamWriter.html) that can write characters to an `OutputStream`
    - `OutputStreamWriter` extends the `Writer` and it needs an `OutputStream` object to be built

---
**Links**
- [Java I/O intro](https://dev.java/learn/java-io/intro/)