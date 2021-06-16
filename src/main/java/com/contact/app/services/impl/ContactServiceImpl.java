package com.contact.app.services.impl;

import com.contact.app.assembler.ContactAssembler;
import com.contact.app.domain.dto.ContactDTO;
import com.contact.app.domain.entities.Contact;
import com.contact.app.exceptions.BadPhoneAndDocNumber;
import com.contact.app.exceptions.ClosedDay;
import com.contact.app.exceptions.ContactsNotFound;
import com.contact.app.infraestructure.repositories.ContactRepository;
import com.contact.app.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ContactAssembler assembler;

    @Autowired
    public ContactServiceImpl(ContactRepository repository, ContactAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public List<ContactDTO> findAllContacts() {

        List<ContactDTO> array = repository.findAll()
                .stream().map(assembler::entityToDto)
                .collect(Collectors.toList());

        if(array.isEmpty()){
            throw new ContactsNotFound("Any contact(s) was found");
        }

        return array;
    }

    @Override
    public ContactDTO save(Contact contact) {

        if(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY)
            throw new ClosedDay("On weekend not working");

        if (contact.getPhone().length()!=10 || contact.getDocumentNumber().length()!=10)
            throw new BadPhoneAndDocNumber("Ilegal param passed");

        repository.save(contact);

        return assembler.entityToDto(contact);
    }

    @Override
    public ContactDTO findById(Long id) {

        Optional<Contact> contact = repository.findById(id);
        if(contact.isEmpty())
            throw new ContactsNotFound("Any contact(s) was found");

        return assembler.entityToDto(contact.get());
    }

    @Override
    public ContactDTO update(Contact contact) {

        if(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY)
            throw new ClosedDay("On weekend not working");

        Contact contacts = repository.findById(contact.getId()).orElse(null);
        if(contacts == null)
            throw new ContactsNotFound("Any contact(s) was found");

        if (contact.getPhone().length()!=10 || contact.getDocumentNumber().length()!=10)
            throw new BadPhoneAndDocNumber("Ilegal param passed");

        contacts = assembler.original(contact);
        repository.save(contacts);

        return assembler.entityToDto(contacts);
    }

    @Override
    public ContactDTO patchName(Long id, String name) {

        Contact contact = repository.findById(id).orElse(null);
        if (contact == null)
            throw new ContactsNotFound("Any contact(s) was found");

        contact.setName(name);
        repository.save(contact);
        return assembler.entityToDto(contact);
    }

    @Override
    public void delete(Long id) {

        Optional<Contact> contact = repository.findById(id);
        if(contact.isEmpty())
            throw new ContactsNotFound("Any contact(s) was found");

        contact.ifPresent(x-> repository.deleteById(contact.get().getId()));
    }
}
