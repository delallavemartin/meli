package com.martindelallave.ml.file;

import java.util.List;

public interface FileReaderService {
  <T> List<T> readDataFromFile(String filePath, Class<T[]> type);
}
