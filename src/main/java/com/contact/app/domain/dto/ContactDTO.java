package com.contact.app.domain.dto;

import com.contact.app.domain.enums.DocumentType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ContactDTO implements Serializable {

    private Long id;
    private String name;
    private String lastName;
    private String phone;
    private LocalDate creation;
    private DocumentType doctype;
    private String documentNumber;

}
