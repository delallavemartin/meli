package com.martindelallave.ml.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

  private static final String VALID_JSON_FILE = "src/test/resources/valid-products.json";
  private static final String INVALID_JSON_FILE = "src/test/resources/invalid-products.json";
  private static final String EMPTY_JSON_FILE = "src/test/resources/empty-products.json";
  private static final String NON_EXISTENT_FILE = "src/test/resources/non-existent.json";

  private FileReaderService fileReaderService;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper();
    fileReaderService = new FileReaderServiceImpl(objectMapper);
  }

  @Test
  void shouldReadValidJsonFile() throws IOException {
    createValidJsonFile();

    List<ProductDetailView> products = fileReaderService.readDataFromFile(VALID_JSON_FILE, ProductDetailView[].class);

    assertNotNull(products);
    assertFalse(products.isEmpty());
    assertEquals(2, products.size());
    assertEquals("1", products.get(0).id());
    assertEquals("Producto 1", products.get(0).title());
  }

  @Test
  void shouldThrowExceptionWhenFileNotFound() {
    Exception exception = assertThrows(RuntimeException.class, () ->
            fileReaderService.readDataFromFile(NON_EXISTENT_FILE, ProductDetailView[].class));

    assertTrue(exception.getMessage().contains("Error reading data from file"));
  }

  @Test
  void shouldReturnEmptyListForEmptyFile() throws IOException {
    createEmptyJsonFile();

    List<ProductDetailView> products = fileReaderService.readDataFromFile(EMPTY_JSON_FILE, ProductDetailView[].class);

    assertNotNull(products);
    assertTrue(products.isEmpty());
  }

  @Test
  void shouldThrowExceptionForInvalidJsonFormat() throws IOException {
    createInvalidJsonFile();

    Exception exception = assertThrows(RuntimeException.class, () ->
            fileReaderService.readDataFromFile(INVALID_JSON_FILE, ProductDetailView[].class));

    assertTrue(exception.getMessage().contains("Error reading data from file:"));
  }

  private void createValidJsonFile() throws IOException {
    String validJson = """
        [
          {
            "id": "1",
            "title": "Producto 1",
            "description": "Descripción 1"
          },
          {
            "id": "2",
            "title": "Producto 2",
            "description": "Descripción 2"
          }
        ]
        """;
    writeToFile(VALID_JSON_FILE, validJson);
  }

  private void createInvalidJsonFile() throws IOException {
    String invalidJson = """
        [
          { "id": "1", "title": "Producto 1", "description": "Descripción 1",
        """;
    writeToFile(INVALID_JSON_FILE, invalidJson);
  }

  private void createEmptyJsonFile() throws IOException {
    writeToFile(EMPTY_JSON_FILE, "[]");
  }

  private void writeToFile(String filePath, String content) throws IOException {
    File file = new File(filePath);
    file.getParentFile().mkdirs();
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(content);
    }
  }
}