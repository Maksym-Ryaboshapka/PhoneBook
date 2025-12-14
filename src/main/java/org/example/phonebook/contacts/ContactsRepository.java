package org.example.phonebook.contacts;

import org.example.phonebook.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByUser(User user);
}
