package org.example.phonebook.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  private static final String SECRET = "Bz56Xr3F4r4kwcrwK3JNNn0w1CqsXACQ44Bp3ShZ3n8=";
  private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(String login) {
    return Jwts.builder()
            .subject(login)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key)
            .compact();
  }

  public boolean isValid(String token) {
    try {
      Jwts.parser()
              .verifyWith(key)
              .build()
              .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractLogin(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }
}
