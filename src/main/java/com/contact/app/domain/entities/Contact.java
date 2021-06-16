package com.contact.app.domain.entities;

import com.contact.app.domain.enums.DocumentType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be empty or blank")
    private String name;
    @NotBlank(message = "LastName cannot be empty or blank")
    private String lastName;
    //Validation in logical service
    private String phone;
    @Column(columnDefinition = "DATE")
    private LocalDate creation;
    private DocumentType doctype;
    //Validation in logical service
    private String documentNumber;


}
