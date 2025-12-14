package org.example.phonebook.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);

    try {
      if (!jwtUtil.isValid(token)) {
        chain.doFilter(request, response);
        return;
      }

      String login = jwtUtil.extractLogin(token);

      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(login, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    } catch (Exception e) {
      SecurityContextHolder.clearContext();
    }

    chain.doFilter(request, response);
  }
}
