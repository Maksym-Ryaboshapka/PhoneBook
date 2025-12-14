package org.example.phonebook.contacts;

import lombok.Data;

@Data
public class ContactsResponseDto {
  private Long id;
  private String name;
  private Long phoneNumber;

  public ContactsResponseDto(Long id, String name, Long phoneNumber) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }
}
