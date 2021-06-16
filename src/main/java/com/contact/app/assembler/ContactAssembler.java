package com.contact.app.assembler;

import com.contact.app.domain.dto.ContactDTO;
import com.contact.app.domain.entities.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactAssembler {

    ContactDTO entityToDto(Contact contact);
    Contact original(Contact contact);

}
