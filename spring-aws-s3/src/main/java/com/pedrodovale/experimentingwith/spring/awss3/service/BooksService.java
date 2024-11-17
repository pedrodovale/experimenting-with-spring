package com.pedrodovale.experimentingwith.spring.awss3.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrodovale.experimentingwith.spring.awss3.BooksProperties;
import com.pedrodovale.experimentingwith.spring.awss3.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class BooksService {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final S3Client s3Client;
  private final String bucket;

  public BooksService(S3Client s3Client, BooksProperties booksProperties) {
    this.s3Client = s3Client;
    this.bucket = booksProperties.getBucket();
  }

  public List<Book> processAndBackup(MultipartFile multipartFile) {
    try (InputStream inputStream = multipartFile.getInputStream()) {
      byte[] bytes = inputStream.readAllBytes();
      List<Book> books = OBJECT_MAPPER.readValue(bytes, new TypeReference<>() {});
      books.forEach(
          book -> {
            String bookId = UUID.randomUUID().toString();
            book.setId(bookId);
            s3Client.putObject(
                PutObjectRequest.builder().key(bookId).bucket(bucket).build(),
                RequestBody.fromBytes(bytes));
          });
      return books;
    } catch (IOException e) {
      throw new RuntimeException("problem retrieving local file", e);
    }
  }
}
