package com.company.crm;

import com.company.crm.domain.model.Account;
import com.company.crm.repository.AccountRepository;
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
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository repo;

    @Test
    void createAndList() throws Exception {
        Account acc = new Account();
        acc.setName("Test");
        repo.save(acc);
        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk());
    }
}
