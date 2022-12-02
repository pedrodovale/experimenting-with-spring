package com.pedrodovale.experimentingwith.spring.permissionevaluator.config;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.repository.BookShelf;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.service.BookClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends GlobalMethodSecurityConfiguration {

  private final BookShelf bookShelf;
  private final BookClubService bookClubService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.httpBasic().and().csrf().disable().build();
  }

  @Bean
  public UserDetailsService users(PasswordEncoder passwordEncoder) {
    UserDetails librarian =
        User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username("alice")
            .password("S3cr3t!")
            .roles("LIBRARIAN")
            .build();
    UserDetails reader =
        User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username("eve")
            .password("S3cr3t!")
            .authorities("ROLE_READER")
            .build();
    UserDetails readerRegistered =
        User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username("bob")
            .password("S3cr3t!")
            .authorities("ROLE_READER", "registered")
            .build();
    return new InMemoryUserDetailsManager(librarian, reader, readerRegistered);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    CustomMethodSecurityExpressionHandler expressionHandler =
        new CustomMethodSecurityExpressionHandler();
    expressionHandler.setBookClubService(bookClubService);
    expressionHandler.setPermissionEvaluator(new BookShelfPermissionEvaluator(bookShelf));
    return expressionHandler;
  }
}
