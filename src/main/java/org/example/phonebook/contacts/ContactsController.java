package org.example.phonebook.contacts;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
  private final ContactsService contactsService;

  public ContactsController(ContactsService contactsService) {
    this.contactsService = contactsService;
  }

  @GetMapping
  public List<ContactsResponseDto> getContactsForUser(Authentication auth) {
    List<Contact> contacts = contactsService.getContactsForUser(auth.getName());

    return contacts.stream()
            .map(c -> new ContactsResponseDto(c.getId(), c.getName(), c.getPhoneNumber()))
            .toList();
  }

  @PostMapping
  public ContactsResponseDto addContact(@RequestBody Contact contact, Authentication auth) {
    Contact newContact = contactsService.addContact(auth.getName(), contact);

    return new ContactsResponseDto(newContact.getId(), newContact.getName(), newContact.getPhoneNumber());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContact(@PathVariable Long id, Authentication auth) {
    boolean deleted = contactsService.deleteContact(auth.getName(), id);

    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
