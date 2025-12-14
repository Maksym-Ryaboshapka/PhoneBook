package org.example.phonebook.contacts;

import org.example.phonebook.users.User;
import org.example.phonebook.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
  private final ContactsRepository contactsRepository;
  private final UserRepository userRepository;

  public ContactsServiceImpl(ContactsRepository contactsRepository, UserRepository userRepository) {
    this.contactsRepository = contactsRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<Contact> getContactsForUser(String login) {
    User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
    return contactsRepository.findByUser(user);
  }

  @Override
  public Contact addContact(String login, Contact contact) {
    User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));

    contact.setUser(user);
    return contactsRepository.save(contact);
  }

  @Override
  public boolean deleteContact(String login, Long contactId) {
    return contactsRepository.findById(contactId)
            .filter(c -> c.getUser().getLogin().equals(login))
            .map(c -> {
              contactsRepository.delete(c);
              return true;
            })
            .orElse(false);
  }
}
