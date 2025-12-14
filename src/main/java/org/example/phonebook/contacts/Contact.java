package org.example.phonebook.contacts;

import jakarta.persistence.*;
import lombok.Data;
import org.example.phonebook.users.User;

@Entity
@Table(name = "contacts")
@Data
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long phoneNumber;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
