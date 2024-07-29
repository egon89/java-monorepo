package com.egon89.outputstream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedOutputStreamExample {
  public static void main(String[] args) {
    byte[] data = new byte[32768]; // 32 KB
    int bufferSize = 16384; // 16 KB buffer

    for (int i = 0; i < data.length; i++) {
      data[i] = (byte) i;
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream("/tmp/output.txt");
         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize)) {
      bufferedOutputStream.write(data);
      bufferedOutputStream.flush();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
