package com.martindelallave.ml.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileReaderServiceImpl implements FileReaderService{
  private final ObjectMapper objectMapper;

  public FileReaderServiceImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public <T> List<T> readDataFromFile(String filePath, Class<T[]> type) {
    File file = new File(filePath);
    try (InputStream inputStream = new FileInputStream(file)) {
      return List.of(objectMapper.readValue(inputStream, type));
    } catch (IOException e) {
      throw new RuntimeException("Error reading data from file: " + filePath, e);
    }
  }
}
