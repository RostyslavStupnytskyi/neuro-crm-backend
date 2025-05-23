package com.company.crm.service;

import com.company.crm.domain.model.Account;
import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    Account findById(Long id);
    void delete(Long id);
}
