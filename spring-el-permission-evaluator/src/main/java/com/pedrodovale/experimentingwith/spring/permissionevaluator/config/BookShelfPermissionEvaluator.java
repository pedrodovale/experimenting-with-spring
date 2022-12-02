package com.pedrodovale.experimentingwith.spring.permissionevaluator.config;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.model.Book;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.repository.BookShelf;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookShelfPermissionEvaluator implements PermissionEvaluator {

  private final BookShelf bookShelf;

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    return false;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {

    if ("rent".equals(permission)) {
      /* books can only be rented by registered users */
      Book book = bookShelf.getBook(targetId.toString());
      if (book != null) {
        return authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> "registered".equals(grantedAuthority.getAuthority()));
      }
    }

    return false;
  }
}
