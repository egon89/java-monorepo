package com.egon89.managingfilesattributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * <a href="https://dev.java/learn/java-io/file-system/metadata/#owner">dev.java</a>
 */
public class SettingFileOrGroupOwner {
  public static void main(String[] args) throws IOException {
    Path file = Path.of("/tmp/file.txt");
    if (Files.notExists(file)) throw new RuntimeException("create the file before continuing");

    UserPrincipalLookupService userPrincipalLookupService = file.getFileSystem().getUserPrincipalLookupService();

    UserPrincipal user = userPrincipalLookupService.lookupPrincipalByName("desired-user");
    Files.setOwner(file, user);

    GroupPrincipal group = userPrincipalLookupService.lookupPrincipalByGroupName("desired-group");
    PosixFileAttributeView fileAttributes = Files.getFileAttributeView(file, PosixFileAttributeView.class);
    fileAttributes.setGroup(group);
  }
}
