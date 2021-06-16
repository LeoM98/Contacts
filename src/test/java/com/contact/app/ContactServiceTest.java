package com.contact.app;

import com.contact.app.domain.entities.Contact;
import com.contact.app.domain.enums.DocumentType;
import com.contact.app.infraestructure.repositories.ContactRepository;
import com.contact.app.util.LocalDateAdapter;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContactsApplication.class)
@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private WebApplicationContext wac;
    @Mock
    private ContactRepository repository;
    private MockMvc mvc;

    // It's so important to execute the test method
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(
                this.wac);
        this.mvc = builder.build();
    }

    @Test
    public void getAllContacts_Status200() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        List<Contact> clienteList = Lists.newArrayList(cliente);
        when(repository.findAll()).thenReturn(clienteList);

        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/api/contacts/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void getContactById_Status200() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/api/contacts/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void giveFillContact_Status201() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void giveEmptyName_Status400() throws Exception {
        Contact cliente = Contact.builder().name("").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void giveEmptyLastName_Status400() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void giveBadNumber_Status400() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("111111111sdgs1")
                .phone("3006366657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void giveBadNumberPhone_Status400() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("300636456657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void BadEndPoint404() throws Exception {
        Contact cliente = Contact.builder().name("Leonardo").lastName("Monsalvo")
                .doctype(DocumentType.CÉDULA).documentNumber("1111111111")
                .phone("300636456657").creation(LocalDate.now())
                .build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/contacdfts/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

}
