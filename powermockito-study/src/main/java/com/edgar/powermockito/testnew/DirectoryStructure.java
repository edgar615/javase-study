package com.edgar.powermockito.testnew;

import java.io.File;

public class DirectoryStructure {
  public boolean create(String directoryPath) {
    File directory = new File(directoryPath);
    if (directory.exists()) {
      throw new IllegalArgumentException(
              "\"" + directoryPath + "\" already exists.");
    }

    return directory.mkdirs();
  }

  public boolean createObj() {
    TestObj testObj = new TestObj();

    return testObj.isResult();
  }
}