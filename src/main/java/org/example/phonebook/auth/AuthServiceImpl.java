package org.example.phonebook.auth;

import org.example.phonebook.users.User;
import org.example.phonebook.users.UserRepository;
import org.example.phonebook.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public String register(String login, String password) {
    if (userRepository.findByLogin(login).isPresent()) {
      throw new RuntimeException("User already exists");
    }

    User user = new User();
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(password));

    userRepository.save(user);

    return jwtUtil.generateToken(login);
  }

  @Override
  public String login(String login, String password) {
    User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Incorrect login or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Incorrect login or password");
    }

    return jwtUtil.generateToken(login);
  }

  @Override
  public boolean exists(String login) {
    User user = userRepository.findByLogin(login).orElse(null);

    if (user == null) {
      return false;
    } else {
      return true;
    }
  }
}
