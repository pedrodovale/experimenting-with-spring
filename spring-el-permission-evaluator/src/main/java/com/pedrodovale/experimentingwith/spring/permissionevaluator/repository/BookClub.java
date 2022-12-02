package com.pedrodovale.experimentingwith.spring.permissionevaluator.repository;

import static java.util.Collections.singletonList;
import static java.util.Collections.synchronizedMap;
import static java.util.Collections.synchronizedSet;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookClubSessionNotStartedException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookNotFoundException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.NotABookClubMemberException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookClub {

  private static final Map<String, Set<String>> CLUB_BOOK_SESSIONS =
      synchronizedMap(new HashMap<>());
  private static final Set<String> CLUB_MEMBERS = synchronizedSet(new HashSet<>());
  private static final Map<String, String> CLUB_MASTERS = synchronizedMap(new HashMap<>());

  public void addReader(String readerUsername) {
    CLUB_MEMBERS.add(readerUsername);
  }

  public void appointClubBookMaster(String bookId, String readerUsername) {
    CLUB_MASTERS.put(bookId, readerUsername);
  }

  public boolean isClubBookMaster(String bookId, String clubBookMasterUsername) {
    if (!CLUB_MASTERS.containsKey(bookId)) {
      throw new BookNotFoundException(bookId);
    }
    return CLUB_MASTERS.get(bookId).equals(clubBookMasterUsername);
  }

  public void startSession(String bookId, String bookMasterUsername) {
    if (!CLUB_BOOK_SESSIONS.containsKey(bookId)) {
      CLUB_BOOK_SESSIONS.put(bookId, new HashSet<>(singletonList(bookMasterUsername)));
    }
  }

  public void attendSession(String bookId, String readerUsername) {
    if (!CLUB_MEMBERS.contains(readerUsername)) {
      throw new NotABookClubMemberException(readerUsername);
    }
    if (!CLUB_BOOK_SESSIONS.containsKey(bookId)) {
      throw new BookClubSessionNotStartedException(bookId);
    }
    CLUB_BOOK_SESSIONS.get(bookId).add(readerUsername);
  }

  public Map<String, Set<String>> getReadersSessions() {
    return CLUB_BOOK_SESSIONS;
  }
}
