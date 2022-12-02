package com.pedrodovale.experimentingwith.spring.permissionevaluator.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotABookClubMemberException extends RuntimeException {

  @Getter private final String readerUsername;
}
