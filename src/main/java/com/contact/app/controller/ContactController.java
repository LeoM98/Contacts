package com.contact.app.controller;

import com.contact.app.domain.dto.ContactDTO;
import com.contact.app.domain.entities.Contact;
import com.contact.app.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactDTO>> findAll(){
        List<ContactDTO> array = service.findAllContacts();
        return new ResponseEntity<>(array, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@PathVariable Long id){
        ContactDTO contact = service.findById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ContactDTO> save(@Valid @RequestBody Contact contact){
        ContactDTO contacts = service.save(contact);
        return new ResponseEntity<>(contacts, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<ContactDTO> update(@Valid @RequestBody Contact contact){
        ContactDTO contacts = service.update(contact);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/{name}")
    public ResponseEntity<ContactDTO> patchName(@PathVariable Long id, @PathVariable String name){
        ContactDTO con = service.patchName(id,name);
        return new ResponseEntity<>(con, HttpStatus.OK);
    }


}
