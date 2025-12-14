package org.example.phonebook.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
    String login = request.get("login");
    String password = request.get("password");

    if (login == null || password == null) {
      return ResponseEntity.badRequest().body(Map.of("error", "Missing login or password"));
    }

    if (authService.exists(login)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(Map.of("error", "User already exists"));
    }

    String token = authService.register(login, password);

    return ResponseEntity.ok(Map.of("token", token));
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
    String login = request.get("login");
    String password = request.get("password");

    if (login == null || password == null) {
      return ResponseEntity.badRequest().body(Map.of("error", "Missing login or password"));
    }

    if (!authService.exists(login)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    }

    String token = authService.login(login, password);

    return ResponseEntity.ok(Map.of("token", token));
  }
}
