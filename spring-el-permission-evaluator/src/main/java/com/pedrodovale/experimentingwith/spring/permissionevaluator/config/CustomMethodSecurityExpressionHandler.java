package com.pedrodovale.experimentingwith.spring.permissionevaluator.config;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.service.BookClubService;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

  private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
  @Setter private BookClubService bookClubService;

  @Override
  protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
      Authentication authentication, MethodInvocation invocation) {
    BookClubMethodSecurityExpressionRoot root =
        new BookClubMethodSecurityExpressionRoot(authentication);
    root.setBookClubService(bookClubService);
    root.setPermissionEvaluator(getPermissionEvaluator());
    root.setTrustResolver(this.trustResolver);
    root.setRoleHierarchy(getRoleHierarchy());
    return root;
  }
}
