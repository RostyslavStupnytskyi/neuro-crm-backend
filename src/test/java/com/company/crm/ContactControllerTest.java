package com.company.crm;

import com.company.crm.domain.model.Contact;
import com.company.crm.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContactRepository repo;

    @Test
    void listContacts() throws Exception {
        Contact c = new Contact();
        c.setFirstName("John");
        repo.save(c);
        mockMvc.perform(get("/api/v1/contacts"))
                .andExpect(status().isOk());
    }
}
