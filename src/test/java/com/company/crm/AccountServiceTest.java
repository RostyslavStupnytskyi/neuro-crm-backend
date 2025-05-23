package com.company.crm;

import com.company.crm.domain.model.Account;
import com.company.crm.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Test
    void saveAndFind() {
        Account a = new Account();
        a.setName("A");
        Account saved = service.save(a);
        assertThat(service.findById(saved.getId())).isNotNull();
    }
}
