package org.example.phonebook.contacts;

import java.util.List;

public interface ContactsService {
  List<Contact> getContactsForUser(String login);
  Contact addContact(String login, Contact contact);
  boolean deleteContact(String login, Long contactId);
}
