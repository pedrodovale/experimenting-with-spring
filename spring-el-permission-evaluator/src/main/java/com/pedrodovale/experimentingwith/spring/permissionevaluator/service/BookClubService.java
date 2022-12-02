package com.pedrodovale.experimentingwith.spring.permissionevaluator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookNotFoundException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.repository.BookClub;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.repository.BookShelf;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookClubService {

  private final BookShelf bookShelf;
  private final BookClub bookClub;
  //  private final UserDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  public void appointClubMaster(String bookId, String readerUsername) {
    if (bookShelf.getBook(bookId) == null) {
      throw new BookNotFoundException(bookId);
    }
    bookClub.appointClubBookMaster(bookId, readerUsername);
  }

  public boolean isClubBookMaster(String bookId, String readerUsername) {
    return bookClub.isClubBookMaster(bookId, readerUsername);
  }

  public void addClubReader(String readerUsername) {
    //    try {
    //      userDetailsService.loadUserByUsername(readerUsername);
    //    } catch (UsernameNotFoundException e) {
    //      throw new BookReaderNotFoundException(readerUsername, e);
    //    }
    bookClub.addReader(readerUsername);
  }

  public void startClubSession(String bookId) {
    String bookMasterUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    bookClub.startSession(bookId, bookMasterUsername);
  }

  public void attendClubSession(String bookId) {
    String bookReaderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    bookClub.attendSession(bookId, bookReaderUsername);
  }

  public JsonNode getReadersSessions() {
    Map<String, Set<String>> readersSessions = bookClub.getReadersSessions();
    try {
      return objectMapper.readTree(objectMapper.writeValueAsString(readersSessions));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
