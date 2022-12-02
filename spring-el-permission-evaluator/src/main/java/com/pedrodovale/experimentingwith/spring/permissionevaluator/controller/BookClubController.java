package com.pedrodovale.experimentingwith.spring.permissionevaluator.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.service.BookClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-club")
@RequiredArgsConstructor
public class BookClubController {

  private final BookClubService bookClubService;

  @GetMapping("/add-reader")
  @PreAuthorize("hasRole('LIBRARIAN')")
  public void addReader(@RequestParam String reader) {
    bookClubService.addClubReader(reader);
  }

  @GetMapping("/appoint-book-master/{bookId}")
  @PreAuthorize("hasRole('LIBRARIAN')")
  public void appointBookClubMaster(@PathVariable String bookId, @RequestParam String reader) {
    bookClubService.appointClubMaster(bookId, reader);
  }

  @GetMapping("/start-session")
  @PreAuthorize("isBookClubMaster(#bookId)")
  public void startBookClubSession(@RequestParam String bookId) {
    bookClubService.startClubSession(bookId);
  }

  @GetMapping("/attend-session")
  @PreAuthorize("hasRole('READER')")
  public void attendBookClubSession(@RequestParam String bookId) {
    bookClubService.attendClubSession(bookId);
  }

  @GetMapping("/get-readers-sessions")
  @PreAuthorize("hasRole('LIBRARIAN')")
  public JsonNode attendBookClubSession() {
    return bookClubService.getReadersSessions();
  }
}
