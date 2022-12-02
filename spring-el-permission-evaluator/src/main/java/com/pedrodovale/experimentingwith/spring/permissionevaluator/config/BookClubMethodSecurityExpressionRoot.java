package com.pedrodovale.experimentingwith.spring.permissionevaluator.config;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookNotFoundException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.service.BookClubService;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class BookClubMethodSecurityExpressionRoot extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {

  private Object filterObject;
  private Object returnObject;
  @Setter private BookClubService bookClubService;

  public BookClubMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  public boolean isBookClubMaster(String bookId) {
    try {
      return bookClubService.isClubBookMaster(bookId, authentication.getName());
    } catch (BookNotFoundException e) {
      return false;
    }
  }

  @Override
  public Object getFilterObject() {
    return this.filterObject;
  }

  @Override
  public Object getReturnObject() {
    return this.returnObject;
  }

  @Override
  public Object getThis() {
    return this;
  }

  @Override
  public void setFilterObject(Object obj) {
    this.filterObject = obj;
  }

  @Override
  public void setReturnObject(Object obj) {
    this.returnObject = obj;
  }
}
