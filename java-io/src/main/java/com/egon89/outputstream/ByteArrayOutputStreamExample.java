package com.egon89.outputstream;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ByteArrayOutputStreamExample {
  public static void main(String[] args) {
    String data = "Lorem ipsum.";
    try(
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("/tmp/manipulated-output.txt")) {
      byteArrayOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
      // manipulate the data (convert to uppercase)
      String originalData = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
      String manipulatedData = originalData.toUpperCase();

      // write the manipulated data to final destination
      fileOutputStream.write(manipulatedData.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
