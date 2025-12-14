package org.example.phonebook.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
  private final String SECRET_BASE64 = "Bz56Xr3F4r4kwcrwK3JNNn0w1CqsXACQ44Bp3ShZ3n8=";
  private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

  private SecretKey key;

  @PostConstruct
  public void init() {
    byte[] decodedKey = Base64.getDecoder().decode(SECRET_BASE64);
    this.key = Keys.hmacShaKeyFor(decodedKey);
  }

  public String generateToken(String login) {
    return Jwts.builder()
            .subject(login)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key)
            .compact();
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
