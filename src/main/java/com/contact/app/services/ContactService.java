package com.contact.app.services;

import com.contact.app.domain.dto.ContactDTO;
import com.contact.app.domain.entities.Contact;

import java.util.List;

public interface ContactService {

    List<ContactDTO> findAllContacts();
    ContactDTO save(Contact contact);
    ContactDTO findById(Long id);
    ContactDTO update(Contact contact);
    ContactDTO patchName(Long id, String name);
    void delete(Long id);

}
