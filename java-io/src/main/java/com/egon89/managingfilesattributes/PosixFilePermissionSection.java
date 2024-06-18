package com.egon89.managingfilesattributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * <a href="https://dev.java/learn/java-io/file-system/metadata/#posix">dev.java</a>
 */
public class PosixFilePermissionSection {
  public static void main(String[] args) throws IOException {
    Path path = Path.of("/tmp/file.txt");
    if (Files.notExists(path)) throw new RuntimeException("create the file before continuing");

    PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class);
    System.out.format("%s %s %s %s%n",
        attributes.owner().getName(),
        attributes.group().getName(),
        attributes.permissions(),
        PosixFilePermissions.toString(attributes.permissions()));
    // user user-group [GROUP_READ, GROUP_WRITE, OWNER_READ, OTHERS_READ, OWNER_WRITE] rw-rw-r--

    // create a set of permission from a string
    Set<PosixFilePermission> manualPermissions = PosixFilePermissions.fromString("rwxrw-r--");
    System.out.println(manualPermissions);
    // [OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_WRITE, OTHERS_READ]

    // assigning the attributes from original file to a new file
    Path path2 = Path.of("/tmp/file2.txt");
    FileAttribute<Set<PosixFilePermission>> fileAttribute =
        PosixFilePermissions.asFileAttribute(attributes.permissions());
    Files.createFile(path2, fileAttribute);

    // assigning permissions on an existing file
    Files.setPosixFilePermissions(path2, manualPermissions);
  }
}
