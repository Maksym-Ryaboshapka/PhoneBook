package org.example.phonebook.auth;

public interface AuthService {
  String register(String login, String password);
  String login(String login, String password);
  boolean exists(String login);
}
